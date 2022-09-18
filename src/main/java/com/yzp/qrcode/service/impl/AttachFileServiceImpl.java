package com.yzp.qrcode.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.dao.AttachFileDao;
import com.yzp.qrcode.entity.AttachFileEntity;
import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.enums.AttachFileType;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.AttachFileService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.service.SysConfigService;
import com.yzp.qrcode.service.UserService;
import com.yzp.qrcode.utils.ImgHandleUtil;
import com.yzp.qrcode.utils.LoginUserInfoUtil;
import com.yzp.qrcode.vo.UploadVo;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
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

    @Resource(/*name = "fileSaveOosServiceImpl"*/)
    private FileSaveService fileSaveService;

    @Resource
    private SysConfigService sysConfigService;

    @Resource
    private UserService userService;

    @Transactional(rollbackFor = {QrCodeAppException.class})
    @Override
    public UploadVo upload(MultipartFile file, String fileName, Integer type) {
        if (file.isEmpty()) {
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件不存在");
        }
        List<Integer> typeList = Arrays.asList(AttachFileType.IMG.getType(), AttachFileType.AUDIO.getType(),
                AttachFileType.VIDEO.getType(), AttachFileType.OTHER.getType());
        if (!typeList.contains(type)) {
            log.error("AttachFileServiceImpl.upload() type类型不存在");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "type类型不存在");
        }
        Long userId = LoginUserInfoUtil.getUserId();
        // 判断个人存储空间是否足够
        UserEntity userEntity = userService.getById(userId);
        if (Objects.isNull(userEntity)) {
            log.error("AttachFileServiceImpl.upload() 用户已不存在");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "用户已不存在");
        }
        // 用户已经使用的存储空间大小
        long fileSizeSum = baseMapper.getFileSizeSumForUser(userId);
        if (fileSizeSum + file.getSize() > userEntity.getFileSpace()) {
            log.error("AttachFileServiceImpl.upload() 个人存储空间不足，无法上传");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "个人存储空间不足，无法上传");
        }
        // 保存文件到存储系统或OOS对象存储服务器
        String path = fileSaveService.save(file);

        // 保存上传信息到文件表
        AttachFileEntity attachFileEntity = new AttachFileEntity();
        attachFileEntity.setFileName(StringUtils.hasText(fileName) ? fileName : IdUtil.fastSimpleUUID());
        attachFileEntity.setFilePath(path);
        attachFileEntity.setFileSize(file.getSize());
        attachFileEntity.setFileType(Objects.requireNonNull(file.getOriginalFilename()).substring(file.getOriginalFilename().lastIndexOf(".") + 1));
        attachFileEntity.setType(type);
        attachFileEntity.setUploadTime(LocalDateTime.now());
        attachFileEntity.setUserId(LoginUserInfoUtil.getUserId());
        // 保存上传记录
        this.save(attachFileEntity);

        // 静态资源域名
        String staticResourceDomain = sysConfigService.getValue(PublicConstant.STATIC_RESOURCE_DOMAIN_NAME);
        String imgUrl = staticResourceDomain + PublicConstant.SLASH + path;
        // TODO 配置域名后可删；此处设置是因为阿里云对象存储OOS没有绑定域名，使用的是阿里云提供的默认域名
        if (fileSaveService instanceof FileSaveOosServiceImpl) {
            String staticResourceDomainNameBackup = sysConfigService.getValue(PublicConstant.STATIC_RESOURCE_DOMAIN_NAME_BACKUP);
            imgUrl = staticResourceDomainNameBackup + PublicConstant.SLASH + path;
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
            response.setHeader("Content-Disposition", String.format("attachment;fileName=\"%s\"", URLEncoder.encode("压缩图片-" + Objects.requireNonNull(file.getOriginalFilename()), "utf-8")));
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
}
