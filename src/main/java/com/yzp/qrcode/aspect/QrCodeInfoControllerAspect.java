package com.yzp.qrcode.aspect;

import com.yzp.qrcode.vo.request.QrCodeInfoReqVo;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.springframework.stereotype.Component;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/10/1 - 12:03
 */
@Slf4j
@Aspect
@Component
public class QrCodeInfoControllerAspect {

    @Before(value = "execution(* com.yzp.qrcode.controller.QrCodeInfoController.getQrCodeInfo(..)) && args(reqVo, ..)")
    public void getQrCodeInfoAspect(JoinPoint pj, QrCodeInfoReqVo reqVo) {
        log.info("请求参数：{}", reqVo);
    }

}
