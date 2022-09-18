package com.yzp.qrcode.utils;

import lombok.Data;
import org.springframework.http.HttpStatus;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/2 - 0:16
 */
@Data
public class R {

    private int code;
    private String message;
    private Object data;

    public R(int code, String message, Object data) {
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public static R success(int code, String message, Object data) {
        return new R(code, message, data);
    }

    public static R success(int code, String message) {
        return success(code, message, null);
    }

    public static R success(int code) {
        return success(code, "success");
    }

    public static R success(String message) {
        return success(HttpStatus.OK.value(), message);
    }

    public static R success(Object data) {
        return success(HttpStatus.OK.value(), "success", data);
    }

    public static R success() {
        return success(HttpStatus.OK.value());
    }

    public static R error(int code, String message, Object data) {
        return new R(code, message, data);
    }

    public static R error(int code, String message) {
        return error(code, message, null);
    }

    public static R error(int code) {
        return error(code, "error");
    }

    public static R error(String message) {
        return error(HttpStatus.BAD_REQUEST.value(), message);
    }

    public static R error(Object data) {
        return error(HttpStatus.BAD_REQUEST.value(), "error", data);
    }

    public static R error() {
        return error(HttpStatus.BAD_REQUEST.value());
    }

    public R putData(Object data) {
        this.setData(data);
        return this;
    }
}
