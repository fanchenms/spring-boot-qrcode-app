package com.yzp.qrcode.vo.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.InputStream;

/**
 * 文件压缩对象
 *
 * @author yzp
 * @version 1.0
 * @date 2022/10/3 - 11:50
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileCompressObject {
    /**
     * 压缩后的文件流
     */
    private InputStream inputStream;

    /**
     * 压缩后文件大小
     */
    private long fileSize;
}
