package com.yzp.qrcode.service.impl;

import com.yzp.qrcode.dao.UserDao;
import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.AttachFileService;
import com.yzp.qrcode.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Slf4j
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {
    @Resource
    private AttachFileService attachFileService;

    @Override
    public boolean isEnoughFileSpace(Long userId, long fileSize) {
        UserEntity userEntity = this.getById(userId);
        if (Objects.isNull(userEntity)) {
            log.error("AttachFileServiceImpl.upload() 用户已不存在");
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "用户已不存在");
        }
        // 用户已经使用的存储空间大小
        long fileSizeSum = attachFileService.getFileSizeSumForUser(userId);
        return fileSizeSum + fileSize <= userEntity.getFileSpace();
    }
    
}
