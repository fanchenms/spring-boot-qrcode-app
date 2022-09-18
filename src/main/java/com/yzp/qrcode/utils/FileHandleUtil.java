package com.yzp.qrcode.utils;

import cn.hutool.core.img.ImgUtil;
import cn.hutool.core.util.IdUtil;
import com.yzp.qrcode.common.PublicConstant;
import org.springframework.util.StringUtils;

import java.io.InputStream;
import java.io.OutputStream;
import java.time.LocalDateTime;

/**
 * 文件名生成工具
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 19:15
 */
public class FileHandleUtil {

    /**
     * 创建唯一的新文件名
     * 例如 图片123.png 返回 2022/09/11/prefix-fjoeafoehfjaoef-suffix.png
     *
     * @param fileName  原来文件名
     * @param prefix    新文件名前缀
     * @param suffix    新文件名后缀
     * @return  文件路径，例如 2022/09/11/prefix-fjoeafoehfjaoef-suffix.png
     */
    public static String getFileName(String fileName, String prefix, String suffix) {
        if (!StringUtils.hasText(fileName)) {
            return null;
        }
        LocalDateTime now = LocalDateTime.now();
        String fileType = fileName.substring(fileName.lastIndexOf(".") + 1);
        String dir = now.getYear() + PublicConstant.SLASH
                + String.format("%02d", now.getMonthValue()) + PublicConstant.SLASH
                + String.format("%02d", now.getDayOfMonth()) + PublicConstant.SLASH;
        String name = (StringUtils.hasText(prefix) ? prefix  + "-" : "")
                + IdUtil.fastSimpleUUID()
                + (StringUtils.hasText(suffix)  ? suffix + "-" : "")
                + PublicConstant.POINT + fileType;
        return dir + name;
    }

}
