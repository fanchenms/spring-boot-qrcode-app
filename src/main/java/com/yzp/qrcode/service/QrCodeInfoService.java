package com.yzp.qrcode.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.yzp.qrcode.entity.QrCodeInfoEntity;
import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;

import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * qrCodeInfoAttachFile 服务类
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
public interface QrCodeInfoService extends IService<QrCodeInfoEntity> {

    /**
     * 获取二维码
     *
     * @param requestParam  请求参数
     * @param response      HttpServletResponse
     * @return  响应内容
     */
    QrCodeInfoRespVo getQrCodeInfo(QrCodeInfoReqVo requestParam, HttpServletResponse response);
}
