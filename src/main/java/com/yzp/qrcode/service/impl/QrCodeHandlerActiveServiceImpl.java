package com.yzp.qrcode.service.impl;

import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.QrCodeHandlerService;
import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;

import javax.servlet.http.HttpServletResponse;

/**
 * 活码
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/18 - 23:39
 */
@Service
public class QrCodeHandlerActiveServiceImpl implements QrCodeHandlerService {
    @Override
    public QrCodeInfoRespVo handler(QrCodeInfoReqVo reqVo, HttpServletResponse response) {
        throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "暂未开放此功能");
    }

    @Override
    public String getSupportedType() {
        return "2";
    }
}
