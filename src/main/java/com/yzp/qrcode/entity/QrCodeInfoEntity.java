package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * <p>
 * qrCodeInfoAttachFile
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qr_code_info")
@ApiModel(value="QrCodeInfoEntity对象", description="qrCodeInfoAttachFile")
public class QrCodeInfoEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "二维码名称")
    private String name;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "类型 1:text文本 2:url网址 3:img图片  4:av音视频    5...后续再说")
    private Integer contentType;

    @ApiModelProperty(value = "码类型 1:静态码【直接显示内容】   2:活码【需要跳转url】")
    private Integer codeType;

    @ApiModelProperty(value = "内容 可以是存文本、HTML、外部网址URL、内网页面URL、内网文件URL(图片音视频文件等)、")
    private String content;

    @ApiModelProperty(value = "跳转的网址【非http开头的需要拼接h5端域名】")
    private String jumpUrl;

    @ApiModelProperty(value = "过期时间")
    private LocalDateTime expirationTime;

    @ApiModelProperty(value = "创建时间")
    private LocalDateTime createTime;

    @ApiModelProperty(value = "修改时间")
    private LocalDateTime updateTime;

    @ApiModelProperty(value = "是否删除 0-否 1-删除")
    private Boolean isDelete;


}
