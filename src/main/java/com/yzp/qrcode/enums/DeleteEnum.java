package com.yzp.qrcode.enums;

/**
 * 是否删除 0-未删除 1-已删除 2-回收站
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/25 - 21:15
 */
public enum DeleteEnum {
    /**
     * 0-删除
     */
    NOT_DELETED(0, "未删除"),
    /**
     * 1-已删除
     */
    DELETED(1, "已删除"),
    /**
     * 2-回收站
     */
    RECYCLE_BIN(2, "回收站"),
    ;
    private final Integer isDelete;
    private final String describe;

    DeleteEnum(Integer isDelete, String describe) {
        this.isDelete = isDelete;
        this.describe = describe;
    }

    public Integer value() {
        return isDelete;
    }

    public String getDescribe() {
        return describe;
    }
}
