package com.yzp.qrcode.vo.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 文件上传对象
 *
 * @author yzp
 * @version 1.0
 * @date 2022/10/3 - 14:24
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileUploadObject {

    private InputStream inputStream;
    private String customizeFileName;
    private Integer type;
    private Integer isCompress;
    private String originalFileName;
    private long fileSize;

}
