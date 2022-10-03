package com.yzp.qrcode.enums;

/**
 * 码类型 1:静态码【直接显示内容】   2:活码【需要跳转url】
 *
 * @author yzp
 * @version 1.0
 * @date 2022/10/1 - 20:47
 */
public enum QrCodeInfoCodeTypeEnum {
    /**
     * 静态码
     */
    STATIC(1, "静态码"),
    /**
     * 活码
     */
    ACTIVITY(2, "活码")
    ;

    private final int codeType;
    private final String describe;

    QrCodeInfoCodeTypeEnum(int codeType, String describe) {
        this.codeType = codeType;
        this.describe = describe;
    }

    public int getValue() {
        return codeType;
    }

    public String getDescribe() {
        return describe;
    }
}
