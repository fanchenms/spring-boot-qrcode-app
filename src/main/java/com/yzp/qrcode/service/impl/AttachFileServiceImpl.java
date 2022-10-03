package com.yzp.qrcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.dao.AttachFileDao;
import com.yzp.qrcode.entity.AttachFileEntity;
import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.enums.AttachFileEnums;
import com.yzp.qrcode.enums.AttachFileTypeEnum;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.AttachFileService;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.service.SysConfigService;
import com.yzp.qrcode.service.UserService;
import com.yzp.qrcode.utils.FileHandleUtil;
import com.yzp.qrcode.utils.ImgHandleUtil;
import com.yzp.qrcode.utils.LoginUserInfoUtil;
import com.yzp.qrcode.vo.UploadVo;
import com.yzp.qrcode.vo.value.FileCompressObject;
import com.yzp.qrcode.vo.value.FileSaveExtraInfo;
import com.yzp.qrcode.vo.value.FileSaveObject;
import com.yzp.qrcode.vo.value.FileUploadObject;
import jdk.internal.util.xml.impl.Input;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URLEncoder;
import java.time.LocalDateTime;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 上传文件记录表 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Slf4j
@Service
public class AttachFileServiceImpl extends ServiceImpl<AttachFileDao, AttachFileEntity> implements AttachFileService {

    @Resource(name = "fileSaveOssServiceImpl")
    private FileSaveService fileSaveService;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private UserService userService;

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public UploadVo upload(MultipartFile file, String fileName, Integer type, Integer isCompress) {
        if (file.isEmpty()) {
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件不存在");
        }
        UploadVo upload;
        try {
            upload = this.upload(new FileUploadObject(file.getInputStream(), fileName, type, isCompress, file.getOriginalFilename(), file.getSize()));
        } catch (IOException e) {
            log.error("AttachFileServiceImpl.upload() 文件保存失败");
            e.printStackTrace();
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件保存失败");
        }
        return upload;
    }

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public UploadVo upload(FileUploadObject fileUploadObject) {
        List<Integer> typeList = Arrays.asList(AttachFileTypeEnum.IMG.getType(), AttachFileTypeEnum.AUDIO.getType(),
                AttachFileTypeEnum.VIDEO.getType(), AttachFileTypeEnum.OTHER.getType());
        if (!typeList.contains(fileUploadObject.getType())) {
            log.error("AttachFileServiceImpl.upload() type类型不存在");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "type类型不存在");
        }
        FileCompressObject fileCompressObject = null;
        // 压缩文件
        if (AttachFileEnums.Compress.COMPRESSED.value().equals(fileUploadObject.getIsCompress())) {
            fileCompressObject = this.compressFile(fileUploadObject.getInputStream(), fileUploadObject.getType(), fileUploadObject.getFileSize());
            fileUploadObject.setInputStream(fileCompressObject.getInputStream());
        }
        long fileSize = Objects.nonNull(fileCompressObject) && fileCompressObject.getFileSize() != 0 ? fileCompressObject.getFileSize() : fileUploadObject.getFileSize();
        Long userId = LoginUserInfoUtil.getUserId();
        // 判断个人存储空间是否足够
        boolean enoughFileSpace = userService.isEnoughFileSpace(userId, fileSize);
        if (!enoughFileSpace) {
            log.error("AttachFileServiceImpl.upload() 个人存储空间不足，无法上传");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "个人存储空间不足，无法上传");
        }
        FileSaveExtraInfo fileSaveExtraInfo = new FileSaveExtraInfo();

        fileSaveExtraInfo.setCustomizeFileName(fileUploadObject.getCustomizeFileName());
        fileSaveExtraInfo.setOriginalFilename(fileUploadObject.getOriginalFileName());
        // 保存文件到存储系统或OOS对象存储服务器
        String filePath = fileSaveService.save(fileUploadObject.getInputStream(), fileSaveExtraInfo);

        // 保存上传信息到文件表
        AttachFileEntity attachFileEntity = new AttachFileEntity();
        attachFileEntity.setFileName(StringUtils.hasText(fileUploadObject.getCustomizeFileName()) ? fileUploadObject.getCustomizeFileName() : FileHandleUtil.getFileName(fileUploadObject.getOriginalFileName()));
        attachFileEntity.setFilePath(filePath);
        attachFileEntity.setFileSize(fileSize);
        attachFileEntity.setFileType(FileHandleUtil.getFileType(fileUploadObject.getOriginalFileName()));
        attachFileEntity.setType(fileUploadObject.getType());
        attachFileEntity.setUploadTime(LocalDateTime.now());
        attachFileEntity.setUserId(LoginUserInfoUtil.getUserId());
        attachFileEntity.setIsCompress(fileUploadObject.getIsCompress());
        // 保存上传记录
        this.save(attachFileEntity);

        // 静态资源域名
        String staticResourceDomain = sysConfigService.getValue(PublicConstant.STATIC_RESOURCE_DOMAIN_NAME);
        String imgUrl = staticResourceDomain + PublicConstant.SLASH + filePath;
        // TODO 配置域名后可删；此处设置是因为阿里云对象存储OOS没有绑定域名，使用的是阿里云提供的默认域名
        if (fileSaveService instanceof FileSaveOssServiceImpl) {
            String staticResourceDomainNameBackup = sysConfigService.getValue(PublicConstant.STATIC_RESOURCE_DOMAIN_NAME_BACKUP);
            imgUrl = staticResourceDomainNameBackup + PublicConstant.SLASH + filePath;
        }
        UploadVo uploadVo = new UploadVo();
        uploadVo.setFileId(attachFileEntity.getFileId());
        uploadVo.setImgUrl(imgUrl);
        return uploadVo;
    }

    @Override
    public void compressPicture(MultipartFile file, float scale, HttpServletResponse response) {
        float scaleMax = 1f;
        float scaleMin = 0f;
        if (scale < scaleMin || scale > scaleMax) {
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "压缩比例设置不正确");
        }
        try {
            response.setContentType("application/octet-stream");
            response.setHeader("content-type", "application/octet-stream");
            response.setHeader("Content-Disposition", String.format("attachment;fileName=\"%s\"",
                    URLEncoder.encode("压缩图片-" + Objects.requireNonNull(file.getOriginalFilename()), "utf-8")));
            if (Objects.equals(scale, scaleMin)) {
                ImgHandleUtil.compressPicture(file.getInputStream(), response.getOutputStream(), file.getSize());
            } else {
                ImgHandleUtil.compressPicture(file.getInputStream(), response.getOutputStream(), scale);
            }
        } catch (IOException e) {
            log.error("压缩图片失败: {}", e.getMessage());
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "压缩图片失败");
        }
    }

    @Override
    public long getFileSizeSumForUser(Long userId) {
        return baseMapper.getFileSizeSumForUser(userId);
    }

    /**
     * 压缩文件
     *
     * @param inputStream 输入流
     * @param fileType 文件类型
     * @return FileCompressObject
     */
    private FileCompressObject compressFile(InputStream inputStream, int fileType, long originalFileSize) {
        FileCompressObject fileCompressObject = new FileCompressObject();
        fileCompressObject.setFileSize(0);
        fileCompressObject.setInputStream(inputStream);
        // 满足条件时压缩图片
        if (AttachFileTypeEnum.IMG.getType() == fileType) {
            byte[] fileBytes = ImgHandleUtil.compressPictureByte(inputStream, originalFileSize);
            fileCompressObject.setFileSize(fileBytes.length);
            fileCompressObject.setInputStream(new ByteArrayInputStream(fileBytes));
        }
        return fileCompressObject;
    }
}
