package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 二维码对应资源表（二维码信息表与文件表为多对多的关系）
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("qr_code_info_attach_file")
@ApiModel(value="QrCodeInfoAttachFileEntity对象", description="二维码对应资源表（二维码信息表与文件表为多对多的关系）")
public class QrCodeInfoAttachFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "二维码信息id")
    private Long qrCodeInfoId;

    @ApiModelProperty(value = "文件id")
    private Long attachFileId;


}
