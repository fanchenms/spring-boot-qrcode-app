package com.yzp.qrcode.exception;

import com.fasterxml.jackson.databind.exc.InvalidFormatException;
import com.yzp.qrcode.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.HashMap;
import java.util.Map;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/4 - 23:46
 */
@Slf4j
@RestControllerAdvice(basePackages = {"com.yzp.qrcode.controller"})
public class ExceptionControllerAdvice {

    /**
     * 方法参数无效异常，处理数据校验
     * @param e 异常
     * @return R
     */
    @ExceptionHandler(value = {MethodArgumentNotValidException.class})
    public R handleValidException(MethodArgumentNotValidException e) {
        log.error("数据校验出现异常:{}, 异常类型:{}",e.getMessage(),e.getClass());
        // 获取数据校验的处理结果
        BindingResult bindingResult = e.getBindingResult();
        Map<String,String> errorMap = new HashMap<>(10);
        bindingResult.getFieldErrors().forEach((fieldError) -> errorMap.put(fieldError.getField(),fieldError.getDefaultMessage()));
        /*StringBuffer msg = new StringBuffer();
        bindingResult.getFieldErrors().forEach((fieldError) -> {
            msg.append(fieldError.getField() + ":" + fieldError.getDefaultMessage() + ";");
        });*/
        // 使用自定义的枚举类，统一异常码和错误信息提示
        return R.error(HttpStatus.BAD_REQUEST.value(), "参数无效").putData(errorMap);
    }


    /**
     * 请求参数与实体类字段类型不同
     *   请求参数与实体类字段类型不同时报异常有两个：1. HttpMessageNotReadableException；2.InvalidFormatException
     * @param ht    异常
     * @param in    异常
     * @return  R
     */
    @ExceptionHandler({HttpMessageNotReadableException.class, InvalidFormatException.class})
    public R handleInvalidFormatException(HttpMessageNotReadableException ht, InvalidFormatException in) {
        log.info("HttpMessageNotReadableException:{}", ht.getMessage());
        log.info("InvalidFormatException无效格式异常:{}", in.getMessage());

        String pathReference = in.getPathReference();
        String[] split = pathReference.split("\"");
        String errorField;
        try {
            errorField = split[1];
        } catch (Exception e) {
            e.printStackTrace();
            return R.error();
        }
        Map<String,Object> map = new HashMap<>(16);
        map.put(errorField, "类型：" + in.getTargetType() + "； 无效值：" + in.getValue());
        return R.error(HttpStatus.BAD_REQUEST.value(), "请求参数与实体类字段类型不同")
                .putData(map);
    }

    /**
     * 运行时异常，主动抛出 (注意方法的顺序)
     * @param e 异常
     * @return  R
     */
    @ExceptionHandler(QrCodeAppException.class)
    public R handleRuntimeException(QrCodeAppException e) {
        log.info("运行时异常：code=[{}] msg=[{}]", e.getCode(), e.getMsg());
        return R.error(e.getCode(), e.getMsg());
    }


    /**
     * 上面捕获不到的异常都交由此处理
     * @param throwable 异常
     * @return  R
     */
    @ExceptionHandler(value = Throwable.class)
    public R handleException(Throwable throwable) {
        log.error("错误：{}",throwable.getMessage());
        throwable.printStackTrace();
        return R.error(HttpStatus.BAD_REQUEST.value(), "未知异常");
    }

}
