package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * <p>
 * 图片样式表
 * </p>
 *
 * @author yzp
 * @since 2022-09-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_picture_style")
@ApiModel(value="PictureStyleEntity对象", description="图片样式表")
public class PictureStyleEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "二维码信息表id")
    private Long qrCodeInfoId;

    @ApiModelProperty(value = "多图展示 0-单图 1-平铺 2-轮播")
    private Integer multiPictureDisplay;

    @ApiModelProperty(value = "图片样式 0-留白 1-撑满 2-置顶")
    private Integer pictureStyle;


}
