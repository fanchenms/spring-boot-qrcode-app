package com.yzp.qrcode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.dao.SysConfigDao;
import com.yzp.qrcode.entity.SysConfigEntity;
import com.yzp.qrcode.service.SysConfigService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统配置表 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-08
 */
@Service
public class SysConfigServiceImpl extends ServiceImpl<SysConfigDao, SysConfigEntity> implements SysConfigService {

    @Cacheable(cacheNames = "Dict", key = "#key")
    @Override
    public String getValue(String key) {
        SysConfigEntity sysConfigEntity = this.getOne(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, key));
        return Objects.isNull(sysConfigEntity) ? null : sysConfigEntity.getParamValue();
    }

    @Override
    public <T> T getSysConfigObject(String key, Class<T> clz) {
        String paramValue = getValue(key);
        if (!StringUtils.hasText(paramValue)) {
            return null;
        }
        if (String.class.getName().equals(clz.getName())) {
            return (T) paramValue;
        }
        return JSON.parseObject(paramValue, clz);
    }

    @Override
    public List<SysConfigEntity> list(String key) {
        return this.list(new LambdaQueryWrapper<SysConfigEntity>().eq(SysConfigEntity::getParamKey, key));
    }
}
