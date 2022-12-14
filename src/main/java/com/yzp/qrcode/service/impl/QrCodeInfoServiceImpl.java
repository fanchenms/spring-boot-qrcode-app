package com.yzp.qrcode.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.yzp.qrcode.dao.QrCodeInfoDao;
import com.yzp.qrcode.entity.QrCodeInfoEntity;
import com.yzp.qrcode.enums.QrCodeInfoCodeTypeEnum;
import com.yzp.qrcode.enums.QrCodeInfoContentTypeEnum;
import com.yzp.qrcode.factory.QrCodeHandlerFactory;
import com.yzp.qrcode.service.QrCodeHandlerService;
import com.yzp.qrcode.service.QrCodeInfoService;
import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * qrCodeInfoAttachFile 服务实现类
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@Service
public class QrCodeInfoServiceImpl extends ServiceImpl<QrCodeInfoDao, QrCodeInfoEntity> implements QrCodeInfoService {

    @Override
    public QrCodeInfoRespVo getQrCodeInfo(QrCodeInfoReqVo requestParam, HttpServletResponse response) {
        int type = QrCodeInfoCodeTypeEnum.STATIC.getValue();
        if (QrCodeInfoContentTypeEnum.getCodeTypeActivityList().contains(Integer.parseInt(requestParam.getContentType()))) {
            type = QrCodeInfoCodeTypeEnum.ACTIVITY.getValue();
        }
        QrCodeHandlerService qrCodeHandlerService = QrCodeHandlerFactory.getInstance(type);
        return qrCodeHandlerService.handler(requestParam, response);
    }
}
