package com.yzp.qrcode.enums;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/25 - 21:22
 */
public class AttachFileEnums {

    public enum Compress {
        /*已压缩*/
        COMPRESSED(1, "已压缩"),
        /*未压缩*/
        UNCOMPRESSED(0,"未压缩"),
        ;
        private final Integer isDelete;
        private final String describe;

        Compress(Integer isDelete, String describe) {
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

}
