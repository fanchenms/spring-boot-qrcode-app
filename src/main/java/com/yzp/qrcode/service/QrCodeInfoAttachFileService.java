package com.yzp.qrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.qrcode.entity.QrCodeInfoAttachFileEntity;

/**
 * <p>
 * 二维码对应资源表（二维码信息表与文件表为多对多的关系） 服务类
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
public interface QrCodeInfoAttachFileService extends IService<QrCodeInfoAttachFileEntity> {

}
