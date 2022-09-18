package com.yzp.qrcode.utils;

import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.service.UserService;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 获取用户登录信息
 * TODO 从Token中解析出当前用户登录信息，此功能未完成，目前只做模拟
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/10 - 11:06
 */
public class LoginUserInfoUtil {

    public static UserEntity getUser() {
        UserEntity userEntity = new UserEntity();
        userEntity.setId(1L);
        userEntity.setUsername("admin");
        userEntity.setPassword("123456");
        userEntity.setNickName("超级用户");
        userEntity.setEmail("yzp163.com");
        userEntity.setFileSpace(50*1024L);
        return userEntity;
    }

    public static Long getUserId() {
        return getUser().getId();
    }

    public static String getUsername() {
        return getUser().getUsername();
    }

}
