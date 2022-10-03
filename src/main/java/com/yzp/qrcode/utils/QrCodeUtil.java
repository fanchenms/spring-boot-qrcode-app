package com.yzp.qrcode.utils;

import com.google.zxing.*;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
import com.google.zxing.qrcode.decoder.ErrorCorrectionLevel;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.geom.RoundRectangle2D;
import java.awt.image.BufferedImage;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.OutputStream;
import java.net.URL;
import java.util.Base64;
import java.util.Hashtable;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/7/26 - 8:59
 * 基于Google开发工具包ZXing生成二维码
 */
@Slf4j
public class QrCodeUtil {

    private static final String CHARSET = "utf-8";
    private static final String FORMAT_NAME = "JPG";
    /** 二维码尺寸 */
    private static final int QRCODE_SIZE = 300;
    /** LOGO宽度 */
    private static final int WIDTH = 90;
    /** LOGO高度 */
    private static final int HEIGHT = 90;

    /**
     * 生成二维码
     * @param content   二维码内容
     * @param imgPath   插图路径
     * @param webImage  是否为网络图片，true-网络图片，false-本地图片
     * @param needCompress  是否需要压缩插图
     * @return  BufferedImage
     * @throws Exception    e
     */
    private static BufferedImage                                                                                                                                                                                                                                                                                                                                                             createImage(String content, String imgPath, boolean webImage, boolean needCompress) throws Exception {
        Hashtable<EncodeHintType, Object> hints = new Hashtable<>();
        /*
         * 误差校正等级，L = ~7% correction、M = ~15% correction、Q = ~25% correction、H = ~30% correction
         * 不设置时，默认为 L 等级，等级不一样，生成的图案不同，但扫描的结果是一样的
         */
        hints.put(EncodeHintType.ERROR_CORRECTION, ErrorCorrectionLevel.H);
        // 字符集编码
        hints.put(EncodeHintType.CHARACTER_SET, CHARSET);
        // 设置二维码边距，单位像素，值越小，二维码距离四周越近
        hints.put(EncodeHintType.MARGIN, 1);
        // 多格式书写器; 这是一个工厂类，它为请求的 BarcodeFormat 找到适当的 Writer 子类，并使用提供的内容对条形码进行编码。
        BitMatrix bitMatrix = new MultiFormatWriter().encode(content, BarcodeFormat.QR_CODE, QRCODE_SIZE, QRCODE_SIZE, hints);

        int width = bitMatrix.getWidth();
        int height = bitMatrix.getHeight();
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        for (int x = 0; x < width; x++) {
            for (int y = 0; y < height; y++) {
                image.setRGB(x, y, bitMatrix.get(x, y) ? 0xFF000000 : 0xFFFFFFFF);
            }
        }
        if (imgPath == null || "".equals(imgPath)) {
            return image;
        }
        // 插入图片
        insertImage(image, imgPath, webImage, needCompress);
        return image;
    }

    /**
     * 插入图片
     * @param source        缓冲图像
     * @param imgPath       插图路径
     * @param webImage      是否为网络图片，true-网络图片，false-本地图片
     * @param needCompress  是否需要压缩插图
     * @throws Exception Exception
     */
    private static void insertImage(BufferedImage source, String imgPath, boolean webImage, boolean needCompress) throws Exception {
        Image src;
        if (webImage) {
            // 网络图片
            if (!StringUtils.hasText(imgPath)) {
                System.err.println("路径不能为空");
                return;
            }
            src = ImageIO.read(new URL(imgPath));
        } else {
            // 本地图片
            File file = new File(imgPath);
            if (!file.exists()) {
                System.err.println("" + imgPath + "   该文件不存在！");
                return;
            }
            src = ImageIO.read(file);
        }

        // 获取二维码图像宽高
        int width = src.getWidth(null);
        int height = src.getHeight(null);
        // 压缩LOGO
        if (needCompress) {
            if (width > WIDTH) {
                width = WIDTH;
            }
            if (height > HEIGHT) {
                height = HEIGHT;
            }
            // 获取扩展实例
            Image image = src.getScaledInstance(width, height, Image.SCALE_SMOOTH);
            BufferedImage tag = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
            // 获取图形
            Graphics g = tag.getGraphics();
            // 绘制缩小后的图
            g.drawImage(image, 0, 0, null);
            g.dispose();
            src = image;
        }
        // 插入LOGO
        // 创建图形
        Graphics2D graph = source.createGraphics();
        int x = (QRCODE_SIZE - width) / 2;
        int y = (QRCODE_SIZE - height) / 2;
        graph.drawImage(src, x, y, width, height, null);
        // 圆角矩形 2D
        Shape shape = new RoundRectangle2D.Float(x, y, width, width, 6, 6);
        // 设置描边
        graph.setStroke(new BasicStroke(3f));
        // 绘画
        graph.draw(shape);
        // 渲染
        graph.dispose();
    }

    /**
     * 创建文件目录
     * @param destPath destPath
     */
    public static void mkdirs(String destPath) {
        File file = new File(destPath);
        // 当文件夹不存在时，mkdirs会自动创建多层目录，区别于mkdir．(mkdir如果父目录不存在则会抛出异常)
        boolean mkdirsResult = false;
        if (!file.exists() && !file.isDirectory()) {
            mkdirsResult = file.mkdirs();
        }
        log.info("创建文件结果：{}", mkdirsResult);
    }


    /**
     * 生成二维码(内嵌LOGO)
     * @param content       内容
     * @param imgPath       要嵌入二维码的图片路径(本地或网络路径)，如果不写或者为null则生成一个没有嵌入图片的纯净的二维码
     * @param webImage      是否为网络图片，true-网络图片，false-本地图片
     * @param destPath      生成的二维码的存放路径; 格式：路径+文件名+图片类型，例如 G:\\QRCode\\abc.png
     * @param needCompress  是否压缩LOGO
     * @throws Exception Exception
     */
    public static void encode(String content, String imgPath, boolean webImage, String destPath, boolean needCompress) throws Exception {
        BufferedImage image = createImage(content, imgPath, webImage, needCompress);
        mkdirs(destPath);
        ImageIO.write(image, FORMAT_NAME, new File(destPath));
    }

    /**
     * 不压缩LOGO
     * @param content   内容
     * @param imgPath   图片路径
     * @param destPath  目标路径
     * @throws Exception Exception
     */
    public static void encode(String content, String imgPath, boolean webImage, String destPath) throws Exception {
        encode(content, imgPath, webImage, destPath, false);
    }

    /**
     * 无LOGO
     * @param content
     * @param destPath
     * @throws Exception
     */
    public static void encode(String content, String destPath) throws Exception {
        encode(content, null,false, destPath, false);
    }

    /**
     * 以流形式返回二维码
     * @param content       二维码中的内容
     * @param imgPath       插图路径
     * @param output        流
     * @param needCompress  是否需要压缩插图
     * @throws Exception
     */
    public static void encode(String content, String imgPath, boolean webImage, OutputStream output, boolean needCompress)
            throws Exception {
        BufferedImage image = createImage(content, imgPath, webImage, needCompress);
        ImageIO.write(image, FORMAT_NAME, output);
    }

    /**
     * 无LOGO输出到流
     * @param content
     * @param output
     * @throws Exception
     */
    public static void encode(String content, OutputStream output) throws Exception {
        encode(content, null, false, output, false);
    }

    /**
     * 以Base64形式返回二维码
     * @param content       二维码中的内容
     * @param imgPath       插图路径
     * @param needCompress  是否需要压缩插图
     * @throws Exception
     */
    public static String encode(String content, String imgPath,boolean webImage, boolean needCompress)
            throws Exception {
        BufferedImage image = createImage(content, imgPath, webImage, needCompress);
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        ImageIO.write(image, FORMAT_NAME, byteArrayOutputStream);
        byteArrayOutputStream.flush();
        byteArrayOutputStream.close();
        return Base64.getEncoder().encodeToString(byteArrayOutputStream.toByteArray());
    }

    /**
     * 无LOGO输出到Base64
     * @param content
     * @throws Exception
     */
    public static String encode(String content) throws Exception {
        return encode(content, null, false, false);
    }

    /**
     * 解析二维码中内容并返回
     * 如果不是二维码图片或没有内容，抛出 NotFoundException 异常
     * @param file
     * @return
     * @throws Exception
     */
    public static String decode(File file) throws Exception {
        BufferedImage image = ImageIO.read(file);
        if (image == null) {
            return null;
        }
        return parseContent(image);
    }

    /**
     * 解析二维码中内容并返回
     * @param url
     * @return
     * @throws Exception
     */
    public static String decode(URL url) throws Exception {
        BufferedImage image = ImageIO.read(url);
        if (image == null) {
            return null;
        }
        return parseContent(image);
    }

    /**
     * 解析二维码中内容
     * @param path      二维码图片路径
     * @param webImage  是否为网络图片，true-网络图片,false-本地文件图片
     * @return
     * @throws Exception
     */
    public static String decode(String path, boolean webImage) throws Exception {
        String content = "";
        if (webImage) {
            content = decode(new URL(path));
        } else {
            content = decode(new File(path));
        }
        return content;
    }

    /**
     * 解析二维码，返回内容
     * @param image
     * @return
     * @throws NotFoundException
     */
    private static String parseContent(BufferedImage image) throws NotFoundException {
        BufferedImageLuminanceSource source = new BufferedImageLuminanceSource(image);
        // 二进制位图
        BinaryBitmap bitmap = new BinaryBitmap(new HybridBinarizer(source));
        Hashtable<DecodeHintType, Object> hints = new Hashtable<>();
        hints.put(DecodeHintType.CHARACTER_SET, CHARSET);
        Result result = new MultiFormatReader().decode(bitmap, hints);
        return result.getText();
    }

}
