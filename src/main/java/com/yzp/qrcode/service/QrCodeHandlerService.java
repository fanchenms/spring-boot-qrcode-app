package com.yzp.qrcode.service;

import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;

import javax.servlet.http.HttpServletResponse;

/**
 * 二维码的处理
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/18 - 22:00
 */
public interface QrCodeHandlerService {

    /**
     * 二维码的处理
     *
     * @param reqVo     请求参数
     * @param response  HttpServletResponse
     * @return  响应内容
     */
    QrCodeInfoRespVo handler(QrCodeInfoReqVo reqVo, HttpServletResponse response);

    /**
     * 获取支持的类型
     *
     * @return str
     */
    String getSupportedType();

}
