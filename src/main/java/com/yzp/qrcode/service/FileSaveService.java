package com.yzp.qrcode.service;

import com.yzp.qrcode.vo.value.FileSaveExtraInfo;
import com.yzp.qrcode.vo.value.FileSaveObject;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;

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
     * @param fileSaveExtraInfo 额外参数
     * @return 文件保存路径
     */
    String save(MultipartFile file, FileSaveExtraInfo fileSaveExtraInfo);

    /**
     * 保存文件
     * 
     * @param inputStream 输入流
     * @param fileName 文件名，可以包含相对路径，例如 2022/10/02/afejofa.png
     * @return 文件保存路径
     */
    String save(InputStream inputStream, String fileName);

    /**
     * 保存文件
     *
     * @param inputStream 输入流
     * @param fileSaveExtraInfo 额外参数
     * @return 文件保存路径
     */
    String save(InputStream inputStream, FileSaveExtraInfo fileSaveExtraInfo);
}
