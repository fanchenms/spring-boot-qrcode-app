package com.yzp.qrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.qrcode.entity.UserEntity;

/**
 * <p>
 * 系统用户表 服务类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
public interface UserService extends IService<UserEntity> {

    /**
     * 根据用户id判断用户是否有足够的存储空间
     * 
     * @param userId 用户id
     * @param fileSize 需要保存的文件大小
     * @return
     */
    boolean isEnoughFileSpace(Long userId, long fileSize);

}
