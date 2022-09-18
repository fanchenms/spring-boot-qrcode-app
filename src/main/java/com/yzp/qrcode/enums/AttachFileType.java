package com.yzp.qrcode.enums;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/11 - 3:58
 */
public enum AttachFileType {
    /** 1:图片 */
    IMG(1, "图片"),
    /** 2:音频 */
    AUDIO(2, "音频"),
    /** 3:视频 */
    VIDEO(3, "视频"),
    /** 4:其他文件 */
    OTHER(4, "其他文件"),
    ;

    private final int type;
    private final String describe;

    AttachFileType(int type, String describe) {
        this.type = type;
        this.describe = describe;
    }

    public int getType() {
        return type;
    }

    public String getDescribe() {
        return describe;
    }
}
