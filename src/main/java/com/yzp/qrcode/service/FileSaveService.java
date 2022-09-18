package com.yzp.qrcode.service;

import org.springframework.web.multipart.MultipartFile;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 0:23
 */
public interface FileSaveService {
    /**
     * 保存文件
     *
     * @param file 文件
     * @return 文件路径
     */
    String save(MultipartFile file);
}
