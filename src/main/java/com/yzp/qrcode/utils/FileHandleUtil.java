package com.yzp.qrcode.utils;

import cn.hutool.core.util.IdUtil;
import com.yzp.qrcode.common.PublicConstant;
import org.springframework.util.StringUtils;

import java.io.File;
import java.io.IOException;
import java.time.Instant;
import java.time.LocalDateTime;
import java.util.Objects;

/**
 * 文件名生成工具
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 19:15
 */
public class FileHandleUtil {

    /**
     * 创建唯一的新文件名,即文件相对路径
     * 例如 图片123.png 返回 2022/09/11/prefix-fjoeafoehfjaoef-suffix.png
     *
     * @param fileName  原来文件名
     * @param prefix    新文件名前缀
     * @param suffix    新文件名后缀
     * @return  文件路径，例如 2022/09/11/prefix-fjoeafoehfjaoef-suffix.png
     */
    public static String getFilePath(String fileName, String prefix, String suffix) {
        if (!StringUtils.hasText(fileName)) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        String fileType = getFileType(fileName);
        String dir = now.getYear() + PublicConstant.SLASH
                + String.format("%02d", now.getMonthValue()) + PublicConstant.SLASH
                + String.format("%02d", now.getDayOfMonth()) + PublicConstant.SLASH;
        String fastSimpleUuid = IdUtil.fastSimpleUUID();
        String name = (StringUtils.hasText(prefix) ? prefix  + "_" : "")
                + Instant.now().toEpochMilli() + "_"
                + fastSimpleUuid.substring(0, fastSimpleUuid.length()/4) + (StringUtils.hasText(suffix) ? "_" : "")
                + (StringUtils.hasText(suffix) ? suffix : "")
                + PublicConstant.POINT + fileType;
        return dir + name;
    }

    /**
     * 获取文件类型
     *
     * @param fileName 文件名称，例如 abc.jpg
     * @return 文件类型，例如 jpg,png,mp4
     */
    public static String getFileType(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            throw new RuntimeException("文件名为空");
        }
        return fileName.substring(fileName.lastIndexOf(".") + 1);
    }

    /**
     * 获取去除后缀的文件名称
     *
     * @param fileName 带后缀的文件名称，例如 abc.jpg
     * @return 文件名称，例如 abc.jpg -> abc
     */
    public static String getFileName(String fileName) {
        if (!StringUtils.hasText(fileName)) {
            throw new RuntimeException("文件名为空");
        }
        return fileName.substring(0, fileName.lastIndexOf("."));
    }

    /**
     * 创建一个文件
     *
     * @param file 文件
     * @return 是否创建成功
     * @throws IOException IOException
     */
    public static boolean createFile(File file) throws IOException {
        if (Objects.isNull(file)) {
            return false;
        }
        File parentFile = file.getParentFile();
        if (!parentFile.exists()) {
            parentFile.mkdirs();
        }
        return file.createNewFile();
    }

}
