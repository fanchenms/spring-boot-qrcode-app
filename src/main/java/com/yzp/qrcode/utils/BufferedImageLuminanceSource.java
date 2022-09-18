package com.yzp.qrcode.utils;

import com.google.zxing.LuminanceSource;

import java.awt.*;
import java.awt.geom.AffineTransform;
import java.awt.image.BufferedImage;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/7/26 - 8:40
 * 二维码的解析需要借助BufferedImageLuminanceSource类，该类是由Google提供的
 * LuminanceSource (亮度源；目的是将跨平台的不同位图实现抽象为用于请求灰度亮度值的标准接口)
 */
public class BufferedImageLuminanceSource extends LuminanceSource {

    private final BufferedImage image;
    private final int left;
    private final int top;

    public BufferedImageLuminanceSource(BufferedImage image) {
        this(image, 0, 0, image.getWidth(), image.getHeight());
    }

    public BufferedImageLuminanceSource(BufferedImage image, int left, int top, int width, int height) {
        super(width, height);

        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();

        if (left + width > sourceWidth || top + height > sourceHeight) {
            throw new IllegalArgumentException("Crop rectangle does not fit within image data.");
        }

        for (int y = top; y < top + height; y++) {
            for (int x = left; x < left + width; x++) {
                if ((image.getRGB(x, y) & 0xFF000000) == 0) {
                    // = white
                    image.setRGB(x, y, 0xFFFFFFFF);
                }
            }
        }

        this.image = new BufferedImage(sourceWidth, sourceHeight, BufferedImage.TYPE_BYTE_GRAY);
        this.image.getGraphics().drawImage(image, 0, 0, null);
        this.left = left;
        this.top = top;
    }

    /**
     * 必须实现的方法
     * 获取行
     * @param y
     * @param row
     * @return
     */
    @Override
    public byte[] getRow(int y, byte[] row) {
        if (y < 0 || y >= getHeight()) {
            throw new IllegalArgumentException("Requested row is outside the image: " + y);
        }
        int width = getWidth();
        if (row == null || row.length < width) {
            row = new byte[width];
        }
        image.getRaster().getDataElements(left, top + y, width, 1, row);
        return row;
    }

    /**
     * 必须实现的方法
     * 获取矩阵
     * @return
     */
    @Override
    public byte[] getMatrix() {
        int width = getWidth();
        int height = getHeight();
        int area = width * height;
        byte[] matrix = new byte[area];
        image.getRaster().getDataElements(left, top, width, height, matrix);
        return matrix;
    }

    /**
     * 是否支持裁剪
     * @return
     */
    @Override
    public boolean isCropSupported() {
        return true;
    }

    /**
     * 裁剪
     * @param left
     * @param top
     * @param width
     * @param height
     * @return
     */
    @Override
    public LuminanceSource crop(int left, int top, int width, int height) {
        return super.crop(left, top, width, height);
    }

    /**
     * 支持旋转
     * @return
     */
    @Override
    public boolean isRotateSupported() {
        return true;
    }

    /**
     * 逆时针旋转
     * @return
     */
    @Override
    public LuminanceSource rotateCounterClockwise() {
        int sourceWidth = image.getWidth();
        int sourceHeight = image.getHeight();
        AffineTransform transform = new AffineTransform(0.0, -1.0, 1.0, 0.0, 0.0, sourceWidth);
        BufferedImage rotatedImage = new BufferedImage(sourceHeight, sourceWidth, BufferedImage.TYPE_BYTE_GRAY);
        Graphics2D g = rotatedImage.createGraphics();
        g.drawImage(image, transform, null);
        g.dispose();
        int width = getWidth();
        return new BufferedImageLuminanceSource(rotatedImage, top, sourceWidth - (left + width), getHeight(), width);
    }
}
