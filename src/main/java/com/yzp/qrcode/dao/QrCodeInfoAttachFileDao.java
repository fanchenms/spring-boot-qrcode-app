package com.yzp.qrcode.dao;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.yzp.qrcode.entity.QrCodeInfoAttachFileEntity;

/**
 * <p>
 * 二维码对应资源表（二维码信息表与文件表为多对多的关系） Mapper 接口
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
public interface QrCodeInfoAttachFileDao extends BaseMapper<QrCodeInfoAttachFileEntity> {

}
