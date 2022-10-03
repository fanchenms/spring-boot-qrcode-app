package com.yzp.qrcode.vo.value;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * 文件保存的额外信息
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/25 - 2:16
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FileSaveExtraInfo {
    /**
     * 自定义文件名称
     */
    private String customizeFileName;
    
    /**
     * 原始文件名称
     */
    private String originalFilename;
}
