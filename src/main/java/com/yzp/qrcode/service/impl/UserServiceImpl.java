package com.yzp.qrcode.service.impl;

import com.yzp.qrcode.dao.UserDao;
import com.yzp.qrcode.entity.UserEntity;
import com.yzp.qrcode.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 系统用户表 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserDao, UserEntity> implements UserService {

}
