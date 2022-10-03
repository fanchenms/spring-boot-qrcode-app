package com.yzp.qrcode.utils;

import cn.hutool.core.img.ImgUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import javax.swing.filechooser.FileSystemView;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Objects;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/12 - 11:55
 */
@Slf4j
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
     * 压缩图片
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
     * 从InputStream流输入到文件File
     *
     * @param inputStream 图片输入流
     * @param file 文件保存位置
     * @param scale 压缩比例
     * @throws FileNotFoundException 表示尝试打开由指定路径名表示的文件失败
     */
    public static void compressPicture(InputStream inputStream, File file, float scale) throws FileNotFoundException {
        compressPicture(inputStream, new FileOutputStream(file), scale);
    }

    /**
     * 从InputStream流输入到文件File
     *
     * @param inputStream 图片输入流
     * @param file 文件保存位置
     * @param fileSize 文件大小
     * @throws FileNotFoundException FileNotFoundException
     */
    public static void compressPicture(InputStream inputStream, File file, long fileSize) throws FileNotFoundException {
        compressPicture(inputStream, new FileOutputStream(file), fileSize);
    }

    /**
     * 从InputStream流输入到指定文件路径
     *
     * @param inputStream 图片输入流
     * @param destImagePath 文件保存位置
     * @param scale 压缩比例
     * @throws FileNotFoundException FileNotFoundException
     */
    public static void compressPicture(InputStream inputStream, String destImagePath, float scale) throws FileNotFoundException {
        compressPicture(inputStream, new FileOutputStream(destImagePath), scale);
    }

    /**
     * 从InputStream流输入到指定文件路径
     *
     * @param inputStream 图片输入流
     * @param destImagePath 文件保存位置
     * @param fileSize 文件大小
     * @throws FileNotFoundException FileNotFoundException
     */
    public static void compressPicture(InputStream inputStream, String destImagePath, long fileSize) throws FileNotFoundException {
        compressPicture(inputStream, new FileOutputStream(destImagePath), fileSize);
    }

    /**
     * 压缩图片,返回字节数组
     *
     * @param inputStream 输入流
     * @param scale 压缩比例
     * @return
     */
    public static byte[] compressPictureByte(InputStream inputStream, float scale) {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        compressPicture(inputStream, outputStream, scale);
        return outputStream.toByteArray();
    }

    /**
     * 压缩图片,返回字节数组
     *
     * @param inputStream 输入流
     * @param fileSize 文件大小
     * @return
     */
    public static byte[] compressPictureByte(InputStream inputStream, long fileSize) {
        return compressPictureByte(inputStream, scale200K(fileSize));
    }

    /**
     * 压缩图片,返回 InputStream 流
     *
     * @param inputStream 输入流
     * @param scale 压缩比例
     * @return InputStream
     */
    public static InputStream compressPicture(InputStream inputStream, float scale) {
        return new ByteArrayInputStream(compressPictureByte(inputStream, scale));
    }

    /**
     * 压缩图片,返回 InputStream 流
     *
     * @param inputStream 输入流
     * @param fileSize 文件大小，单位 B
     * @return InputStream
     */
    public static InputStream compressPicture(InputStream inputStream, long fileSize) {
        return compressPicture(inputStream, scale200K(fileSize));
    }

    /**
     * 压缩图片,返回压缩后文件大小
     *
     * @param inputStream 输入流
     * @param file 文件保存位置
     * @param scale 压缩比例
     * @return 压缩后文件大小
     */
    public static long compressPictureSize(InputStream inputStream, File file, float scale) {
        long size;
        try {
            boolean isCreate = createFile(file);
            if (!isCreate) {
                throw new RuntimeException("文件创建失败");
            }
            size = compressPictureSize(inputStream, new FileOutputStream(file), scale);
        } catch (Exception e) {
            e.printStackTrace();
            throw new RuntimeException("压缩失败");
        }
        return size;
    }

    /**
     * 压缩图片,返回压缩后文件大小
     *
     * @param inputStream 输入流
     * @param file 文件保存位置
     * @param fileSize 文件大小
     * @return 压缩后文件大小
     */
    public static long compressPictureSize(InputStream inputStream, File file, long fileSize) {
        return compressPictureSize(inputStream, file, scale200K(fileSize));
    }

    /**
     * 压缩图片
     *
     * @param inputStream 输入流
     * @param savePath 文件保存路径
     * @param scale 压缩比例
     * @return 压缩后文件大小
     */
    public static long compressPictureSize(InputStream inputStream, String savePath, float scale) {
        long size;
        try {
            size = compressPictureSize(inputStream, new FileOutputStream(savePath), scale);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            throw new RuntimeException("文件未找到异常");
        }
        return size;
    }

    /**
     * 压缩图片
     *
     * @param inputStream 输入流
     * @param savePath 文件保存路径
     * @param fileSize 原始文件大小
     * @return 压缩后文件大小
     */
    public static long compressPictureSize(InputStream inputStream, String savePath, long fileSize) {
        return compressPictureSize(inputStream, savePath, scale200K(fileSize));
    }

    /**
     * 压缩图片
     *
     * @param inputStream       输入流
     * @param fileOutputStream  文件输出流
     * @param scale 压缩比例
     * @return 压缩后文件大小
     */
    public static long compressPictureSize(InputStream inputStream, FileOutputStream fileOutputStream, float scale) {
        byte[] bytes;
        try {
            if (Objects.isNull(fileOutputStream)) {
                throw new RuntimeException("FileOutputStream不能为空");
            }
            bytes = compressPictureByte(inputStream, scale);
            fileOutputStream.write(bytes);
            fileOutputStream.flush();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RuntimeException("保存失败");
        } finally {
            if (Objects.nonNull(fileOutputStream)) {
                try {
                    fileOutputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return bytes.length;
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

    /**
     * 创建一个文件
     *
     * @param file 文件
     * @return 是否创建成功
     * @throws IOException IOException
     */
    private static boolean createFile(File file) throws IOException {
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
