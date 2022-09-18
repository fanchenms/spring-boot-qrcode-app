package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 系统用户表
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_user")
@ApiModel(value="UserEntity对象", description="用户表")
public class UserEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "用户id")
    @TableId(value = "id", type = IdType.AUTO)
    private Long id;

    @ApiModelProperty(value = "用户名")
    private String username;

    @ApiModelProperty(value = "昵称")
    private String nickName;

    @ApiModelProperty(value = "密码")
    private String password;

    @ApiModelProperty(value = "联系电话")
    private String phone;

    @ApiModelProperty(value = "性别 0-男 1-女")
    private Integer sex;

    @ApiModelProperty(value = "生日")
    private LocalDateTime birthday;

    @ApiModelProperty(value = "职业")
    private String profession;

    @ApiModelProperty(value = "邮箱")
    private String email;

    @ApiModelProperty(value = "文件空间大小，默认50*1024B(50MB)")
    private Long fileSpace;

}
