package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统字典表
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_dict")
@ApiModel(value="QDictEntity对象", description="系统字典表")
public class DictEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "主键id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "字典key")
    private String dictKey;

    @ApiModelProperty(value = "字段值")
    private String dictValue;

    @ApiModelProperty(value = "字典名称")
    private String dictName;

}
