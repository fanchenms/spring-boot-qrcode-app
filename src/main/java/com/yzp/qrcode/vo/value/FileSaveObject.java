package com.yzp.qrcode.vo.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/25 - 20:10
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSaveObject {
    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件大小
     */
    private Long fileSize;
}
