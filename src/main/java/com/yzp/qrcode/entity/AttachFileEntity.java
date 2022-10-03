package com.yzp.qrcode.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import java.time.LocalDateTime;
import java.io.Serializable;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * <p>
 * 上传文件记录表
 * </p>
 *
 * @author yzp
 * @since 2022-09-04
 */
@Data
@EqualsAndHashCode(callSuper = false)
@TableName("q_attach_file")
@ApiModel(value="QAttachFileEntity对象", description="上传文件记录表")
public class AttachFileEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    @TableId(value = "file_id", type = IdType.AUTO)
    private Long fileId;

    @ApiModelProperty(value = "文件路径")
    private String filePath;

    @ApiModelProperty(value = "文件类型 【扩展名,如png、mp4】")
    private String fileType;

    @ApiModelProperty(value = "文件名")
    private String fileName;

    @ApiModelProperty(value = "文件大小")
    private Long fileSize;

    @ApiModelProperty(value = "上传时间")
    private LocalDateTime uploadTime;

    @ApiModelProperty(value = "用户id")
    private Long userId;

    @ApiModelProperty(value = "文件 1:图片 2:音频 3:视频 4:其他文件")
    private Integer type;

    @ApiModelProperty(value = "文件夹")
    private String dir;

    @ApiModelProperty(value = "是否删除 0-未删除 1-已删除 2-回收站")
    private Integer isDelete;

    @ApiModelProperty(value = "是否压缩 0-未压缩 1-压缩")
    private Integer isCompress;

}
