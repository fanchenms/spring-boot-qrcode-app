package com.yzp.qrcode.vo.request;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Pattern;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/17 - 17:14
 */
@Data
@Api(tags = "生成二维码请求参数实体")
public class QrCodeInfoReqVo {

    @Pattern(regexp = "[1-7]", message = "必须为1、2、3、4、5、6、7之一")
    @ApiModelProperty(value = "内容类型 1:text文本 2:url网址 3:img图片 4:audio音频 5:video视频 6:文件 7:HTML 8...后续再说")
    private String contentType;

    @NotBlank(message = "二维码内容不能为空")
    @ApiModelProperty(value = "内容 可以是存文本、HTML、外部网址URL、内网页面URL、内网文件URL(图片音视频文件等)、")
    private String content;

    @ApiModelProperty(value = "二维码名称")
    private String name;
    
    @ApiModelProperty(value = "二维码插图")
    private String qrCodeIllustrationPath;

}
