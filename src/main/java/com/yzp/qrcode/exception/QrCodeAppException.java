package com.yzp.qrcode.exception;

/**
 * 自定义异常
 * @author yzp
 * @version 1.0
 * @date 2022/9/4 - 23:48
 */
public class QrCodeAppException extends RuntimeException {

    private int code;
    private String msg;

    public QrCodeAppException() {
    }

    public QrCodeAppException(int code,String msg) {
        this.code = code;
        this.msg = msg;
    }


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

}
