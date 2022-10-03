package com.yzp.qrcode.service.impl;

import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.exception.QrCodeAppException;
import com.yzp.qrcode.service.FileSaveService;
import com.yzp.qrcode.utils.FileHandleUtil;
import com.yzp.qrcode.vo.value.FileSaveExtraInfo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Primary;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;

/**
 * 本地文件存储
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 0:24
 */
@Slf4j
@Primary
@Service
public class FileSaveLocalServiceImpl implements FileSaveService {

    @Override
    public String save(MultipartFile file, FileSaveExtraInfo fileSaveExtraInfo) {
        String filePath = "";
        try {
            filePath = FileHandleUtil.getFilePath(file.getOriginalFilename(),
                    StringUtils.hasText(fileSaveExtraInfo.getCustomizeFileName()) ? fileSaveExtraInfo.getCustomizeFileName() : FileHandleUtil.getFileName(file.getOriginalFilename()),
                    null);
            String savePath = PublicConstant.WIN_FILE_PATH + filePath;
            File saveFile = new File(savePath);
            boolean isCreateFile = FileHandleUtil.createFile(saveFile);
            if (!isCreateFile) {
                throw new RuntimeException("文件创建失败");
            }
            // 不压缩
            file.transferTo(saveFile);
            return filePath;
        } catch (Exception e) {
            log.error("报错信息：FileSaveLocalServiceImpl.save() 保存文件出错 {}", e.getMessage());
            e.printStackTrace();
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件保存失败");
        }
    }

    @Override
    public String save(InputStream inputStream, FileSaveExtraInfo fileSaveExtraInfo) {
        String originalFilename = fileSaveExtraInfo.getOriginalFilename();
        String originalFileNameNoType = FileHandleUtil.getFileName(originalFilename);
        String customizeFileName = fileSaveExtraInfo.getCustomizeFileName();
        String filePath = FileHandleUtil.getFilePath(originalFilename,
                StringUtils.hasText(customizeFileName) ? customizeFileName : originalFileNameNoType,
                null);
        this.save(inputStream, filePath);
        return filePath;
    }

    @Override
    public String save(InputStream inputStream, String fileName) {
        FileOutputStream fileOutputStream = null;
        try {
            String savePath = PublicConstant.WIN_FILE_PATH + fileName;
            File saveFile = new File(savePath);
            boolean isCreateFile = FileHandleUtil.createFile(saveFile);
            if (!isCreateFile) {
                throw new RuntimeException("文件创建失败");
            }
            fileOutputStream = new FileOutputStream(saveFile);
            byte[] buf = new byte[1024];
            int read;
            while ((read = inputStream.read(buf)) != -1) {
                fileOutputStream.write(buf, 0, read);
            }
            fileOutputStream.flush();
        } catch (IOException e) {
            log.error("报错信息：FileSaveLocalServiceImpl.save() 保存文件出错 {}", e.getMessage());
            e.printStackTrace();
            throw new QrCodeAppException(HttpStatus.BAD_REQUEST.value(), "文件保存失败");
        } finally {
            if (fileOutputStream != null) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return fileName;
    }

}
