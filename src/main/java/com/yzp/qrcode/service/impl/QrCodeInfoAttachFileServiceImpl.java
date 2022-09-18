package com.yzp.qrcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.dao.QrCodeInfoAttachFileDao;
import com.yzp.qrcode.entity.QrCodeInfoAttachFileEntity;
import com.yzp.qrcode.service.QrCodeInfoAttachFileService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 二维码对应资源表（二维码信息表与文件表为多对多的关系） 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@Service
public class QrCodeInfoAttachFileServiceImpl extends ServiceImpl<QrCodeInfoAttachFileDao, QrCodeInfoAttachFileEntity> implements QrCodeInfoAttachFileService {

}
