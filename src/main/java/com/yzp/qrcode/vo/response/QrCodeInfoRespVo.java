package com.yzp.qrcode.vo.response;

import io.swagger.annotations.Api;
import lombok.Data;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/17 - 17:14
 */
@Data
@Api(tags = "二维码响应参数实体")
public class QrCodeInfoRespVo {
    /**
     * 二维码图片,URL或Base64
     */
    private String imgSrc;

}
