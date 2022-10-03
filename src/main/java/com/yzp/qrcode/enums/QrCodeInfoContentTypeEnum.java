package com.yzp.qrcode.enums;

import java.util.Arrays;
import java.util.List;

/**
 * 内容类型 1:text文本 2:url网址 3:img图片 4:audio音频 5:video视频 6:文件 7:HTML 8...后续再说
 *
 * @author yzp
 * @version 1.0
 * @date 2022/10/1 - 20:39
 */
public enum  QrCodeInfoContentTypeEnum {
    /**
     * 文本
     */
    TEXT(1, "文本"),
    /**
     * 网址
     */
    URL(2, "网址"),
    /**
     * 图片
     */
    IMG(3, "图片"),
    /**
     * 音频
     */
    AUDIO(4, "音频"),
    /**
     * 视频
     */
    VIDEO(5, "视频"),
    /**
     * 文件
     */
    DOCUMENT(6, "文件"),
    /**
     * HTML
     */
    HTML(7, "HTML"),

    ;

    private final int contentType;
    private final String describe;

    QrCodeInfoContentTypeEnum(int contentType, String describe) {
        this.contentType = contentType;
        this.describe = describe;
    }

    public int value() {
        return contentType;
    }

    public String getDescribe() {
        return describe;
    }

    public static List<Integer> getCodeTypeStaticList() {
        return Arrays.asList(TEXT.value(), URL.value(), HTML.value());
    }

    public static List<Integer> getCodeTypeActivityList() {
        return Arrays.asList(IMG.value(), AUDIO.value(), VIDEO.value(), DOCUMENT.value());
    }
}
