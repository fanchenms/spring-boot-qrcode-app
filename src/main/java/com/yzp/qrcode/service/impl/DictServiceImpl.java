package com.yzp.qrcode.service.impl;

import com.alibaba.fastjson.JSON;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.dao.DictDao;
import com.yzp.qrcode.entity.DictEntity;
import com.yzp.qrcode.service.DictService;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.Objects;

/**
 * <p>
 * 系统字典表 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Service
public class DictServiceImpl extends ServiceImpl<DictDao, DictEntity> implements DictService {

    @Cacheable(cacheNames = "Dict", key = "#key")
    @Override
    public String getValue(String key) {
        DictEntity dictEntity = this.getOne(new LambdaQueryWrapper<DictEntity>().eq(DictEntity::getDictKey, key));
        return Objects.isNull(dictEntity) ? null : dictEntity.getDictValue();
    }

    @Override
    public <T> T getDictObject(String key, Class<T> clz) {
        String dictValue = getValue(key);
        if (!StringUtils.hasText(dictValue)) {
            return null;
        }
        if (String.class.getName().equals(clz.getName())) {
            return (T) dictValue;
        }
        return JSON.parseObject(dictValue, clz);
    }

    @Override
    public List<DictEntity> list(String key) {
        return this.list(new LambdaQueryWrapper<DictEntity>().eq(DictEntity::getDictKey, key));
    }
}
