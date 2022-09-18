package com.yzp.qrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.qrcode.entity.DictEntity;

import java.util.List;

/**
 * <p>
 * 系统字典表 服务类
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
public interface DictService extends IService<DictEntity> {

    /**
     * 根据 key 获取值
     * @param key   字典key
     * @return 值
     */
    String getValue(String key);

    /**
     * 根据 key 获取字典值，返回指定类型，一般内容为 json 格式
     * @param key   字典key
     * @param clz   返回类型
     * @param <T>   返回类型
     * @return  返回指定类型
     */
    <T> T getDictObject(String key, Class<T> clz);

    /**
     * 根据 key 获取字典值,返回集合
     * @param key 字典key
     * @return 列表
     */
    List<DictEntity> list(String key);
}
