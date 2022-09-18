package com.yzp.qrcode.service.impl;

import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.utils.FileHandleUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 0:24
 */
@Slf4j
@Primary
@Service
public class FileSaveLocalServiceImpl implements FileSaveService {
    @Override
    public String save(MultipartFile file) {
        String filePath = FileHandleUtil.getFileName(file.getOriginalFilename(), null, null);
        try {
            File saveFile = new File(PublicConstant.WIN_FILE_PATH + filePath);
            boolean mkdirsResult = false;
            if (!saveFile.exists()) {
                mkdirsResult = saveFile.mkdirs();
            }
            log.warn("文件保存状态：{}", mkdirsResult);
            file.transferTo(saveFile);
        } catch (IOException e) {
            log.error("报错信息：FileSaveLocalServiceImpl.save() 保存文件出错 {}", e.getMessage());
            e.printStackTrace();
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件保存失败");
        }
        return filePath;
    }
}
