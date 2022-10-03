package com.yzp.qrcode.service.impl;

import cn.hutool.core.util.IdUtil;
import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.entity.QrCodeInfoAttachFileEntity;
import com.yzp.qrcode.entity.QrCodeInfoEntity;
import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.enums.AttachFileEnums;
import com.yzp.qrcode.enums.AttachFileTypeEnum;
import com.yzp.qrcode.enums.QrCodeInfoCodeTypeEnum;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.AttachFileService;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.service.QrCodeHandlerService;
import com.yzp.qrcode.service.QrCodeInfoAttachFileService;
import com.yzp.qrcode.service.QrCodeInfoService;
import com.yzp.qrcode.service.SysConfigService;
import com.yzp.qrcode.utils.LoginUserInfoUtil;
import com.yzp.qrcode.utils.QrCodeUtil;
import com.yzp.qrcode.vo.UploadVo;
import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;
import com.yzp.qrcode.vo.value.FileSaveExtraInfo;
import com.yzp.qrcode.vo.value.FileUploadObject;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Objects;

/**
 * 静态码
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/18 - 23:38
 */
@Service
public class QrCodeHandlerStaticServiceImpl implements QrCodeHandlerService {
    @Resource
    private QrCodeInfoService qrCodeInfoService;

    @Resource
    private AttachFileService attachFileService;

    @Resource
    private QrCodeInfoAttachFileService qrCodeInfoAttachFileService;

    /**
     * 静态码文件名称前缀
     */
    private static final String QR_CODE_FILE_NAME_PREFIX = "static_code";

    @Transactional(rollbackFor = {Exception.class})
    @Override
    public QrCodeInfoRespVo handler(QrCodeInfoReqVo reqVo, HttpServletResponse response) {
        QrCodeInfoRespVo qrCodeInfoRespVo = new QrCodeInfoRespVo();
        try {
            String content = reqVo.getContent();
            // 是否是登录状态；是-二维码图片入库，否-临时二维码
            UserEntity user = LoginUserInfoUtil.getUser();
            if (Objects.isNull(user)) {
                String imgBase64 = QrCodeUtil.encode(content);
                qrCodeInfoRespVo.setImgSrc(PublicConstant.BASE64_IMAGE_PREFIX + imgBase64);
                return qrCodeInfoRespVo;
            }
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            if (StringUtils.hasText(reqVo.getQrCodeIllustrationPath())) {
                QrCodeUtil.encode(content, reqVo.getQrCodeIllustrationPath(), true, byteArrayOutputStream, true);
            } else {
                QrCodeUtil.encode(content, byteArrayOutputStream);
            }
            byte[] bytes = byteArrayOutputStream.toByteArray();
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
            FileSaveExtraInfo fileSaveExtraInfo = new FileSaveExtraInfo();
            fileSaveExtraInfo.setOriginalFilename(".png");
            fileSaveExtraInfo.setCustomizeFileName(QR_CODE_FILE_NAME_PREFIX);

            // 上传文件
            UploadVo upload = attachFileService.upload(new FileUploadObject(byteArrayInputStream, QR_CODE_FILE_NAME_PREFIX,
                    AttachFileTypeEnum.IMG.getType(), AttachFileEnums.Compress.UNCOMPRESSED.value(),
                    ".png", bytes.length));
            // 设置返回值对象的URL地址
            qrCodeInfoRespVo.setImgSrc(upload.getImgUrl());

            // 保存二维码信息
            LocalDateTime now = LocalDateTime.now();
            QrCodeInfoEntity qrCodeInfoEntity = new QrCodeInfoEntity();
            qrCodeInfoEntity.setName(StringUtils.hasText(reqVo.getName()) ? reqVo.getName() : IdUtil.fastSimpleUUID());
            qrCodeInfoEntity.setUserId(user.getId());
            qrCodeInfoEntity.setContentType(Integer.parseInt(reqVo.getContentType()));
            qrCodeInfoEntity.setCodeType(QrCodeInfoCodeTypeEnum.STATIC.getValue());
            qrCodeInfoEntity.setContent(content);
            qrCodeInfoEntity.setExpirationTime(LocalDateTime.parse(PublicConstant.PERMANENT_TIME, DateTimeFormatter.ofPattern(PublicConstant.DATE_FORMAT_DATE_TIME)));
            qrCodeInfoEntity.setCreateTime(now);
            qrCodeInfoEntity.setUpdateTime(now);
            qrCodeInfoEntity.setEffectTime(now);
            qrCodeInfoService.save(qrCodeInfoEntity);

            // 保存二维码与文件的对应关系
            QrCodeInfoAttachFileEntity qrCodeInfoAttachFileEntity = new QrCodeInfoAttachFileEntity();
            qrCodeInfoAttachFileEntity.setQrCodeInfoId(qrCodeInfoEntity.getId());
            qrCodeInfoAttachFileEntity.setAttachFileId(upload.getFileId());
            qrCodeInfoAttachFileService.save(qrCodeInfoAttachFileEntity);
        } catch (Exception e) {
            e.printStackTrace();
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "生成二维码失败");
        }
        return qrCodeInfoRespVo;
    }

    @Override
    public String getSupportedType() {
        return "1";
    }
}
