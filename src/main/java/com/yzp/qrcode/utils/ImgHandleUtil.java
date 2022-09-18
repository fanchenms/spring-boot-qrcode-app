package com.yzp.qrcode.utils;

import cn.hutool.core.img.ImgUtil;
import org.springframework.util.StringUtils;

import javax.swing.filechooser.FileSystemView;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Objects;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/12 - 11:55
 */
public class ImgHandleUtil {

    /**
     * 压缩图片
     *
     * @param srcImagePath  源文件路径
     * @param destImagePath 保存路径
     */
    public static void compressPicture(String srcImagePath, String destImagePath) {
        compressPicture(srcImagePath, destImagePath, scale200K(new File(srcImagePath).length()));
    }

    /**
     * 压缩图片
     *
     * @param srcImagePath  源文件路径
     * @param destImagePath 保存路径
     * @param scale         压缩比例
     */
    public static void compressPicture(String srcImagePath, String destImagePath, float scale) {
        compressPicture(new File(srcImagePath), StringUtils.hasText(destImagePath) ? new File(destImagePath) : null, scale);
    }

    /**
     *
     * @param srcImageFile  源文件路径
     * @param destImageFile 保存路径
     * @param scale         压缩比例
     */
    public static void compressPicture(File srcImageFile, File destImageFile, float scale) {
        if (Objects.isNull(srcImageFile) || !srcImageFile.exists()) {
            throw new RuntimeException("文件不存在");
        }
        if (Objects.isNull(destImageFile)) {
            FileSystemView fileSystemView = FileSystemView.getFileSystemView();
            File homeDirectory = fileSystemView.getHomeDirectory();
            String homePath = homeDirectory.getPath();
            String filePath = homePath.replace("\\\\", "//") + "\\" + srcImageFile.getName();
            System.out.println("桌面路径：" + filePath);
            // 默认保存到桌面
            destImageFile = new File(filePath);
        }
        boolean createNewFileResult;
        if (!destImageFile.exists()) {
            try {
                createNewFileResult = destImageFile.createNewFile();
                if (!createNewFileResult) {
                    throw new RuntimeException("文件创建失败");
                }
            } catch (IOException e) {
                e.printStackTrace();
                throw new RuntimeException("文件创建失败");
            }
        }
        ImgUtil.scale(srcImageFile, destImageFile, scale);
    }

    /**
     * 压缩图片
     *
     * @param inputStream   图片输入流
     * @param outputStream  图片输出流
     * @param scale 压缩比例 0~1
     */
    public static void compressPicture(InputStream inputStream, OutputStream outputStream, float scale) {
        ImgUtil.scale(inputStream, outputStream, scale);
    }

    /**
     * 压缩图片
     *
     * @param inputStream   图片输入流
     * @param outputStream  图片输出流
     * @param fileSize      文件大小
     */
    public static void compressPicture(InputStream inputStream, OutputStream outputStream, long fileSize) {
        compressPicture(inputStream, outputStream, scale200K(fileSize));
    }

    /**
     * 获取到200kb左右的压缩scale值
     * 一一手动校验值（经验值）
     *
     * @param srcSize 文件长度
     * @return scale 压缩比例
     */
    private static float scale200K(long srcSize) {
        float scale;
        if (srcSize < 200*1000) {
            scale = 1.00f;
        } else if (srcSize < 500*1000) {
            scale = 0.57f;
        } else if (srcSize < 700*1000) {
            scale = 0.47f;
        } else if (srcSize < 1000*1000) {
            scale = 0.37f;
        } else if (srcSize < 2*1000*1000) {
            scale = 0.25f;
        } else if (srcSize < 4*1000*1000) {
            scale = 0.17f;
        } else if (srcSize < 5*1000*1000) {
            scale = 0.13f;
        } else if (srcSize < 10*1000*1000) {
            scale = 0.10f;
        } else {
            scale = 0.01f;
        }
        return scale;
    }

}
