package com.yzp.qrcode.controller;

import com.yzp.qrcode.service.QrCodeInfoService;
import com.yzp.qrcode.utils.R;
import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import com.yzp.qrcode.vo.response.QrCodeInfoRespVo;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>
 * qrCodeInfoAttachFile 前端控制器
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@RestController
@RequestMapping("/qrCodeInfo")
public class QrCodeInfoController {

    @Resource
    private QrCodeInfoService qrCodeInfoService;

    @PostMapping("/")
    public R getQrCodeInfo(@RequestBody QrCodeInfoReqVo requestParam, HttpServletResponse response) {
        QrCodeInfoRespVo qrCodeInfoRespVo = qrCodeInfoService.getQrCodeInfo(requestParam, response);
        return R.success(qrCodeInfoRespVo);
    }

}
