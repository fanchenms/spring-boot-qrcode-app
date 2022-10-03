package com.yzp.qrcode.factory;

import com.yzp.qrcode.service.QrCodeHandlerService;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

/**
 * 二维码处理工厂
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/18 - 23:40
 */
@Component
public class QrCodeHandlerFactory implements ApplicationContextAware {

    private static final Map<String, QrCodeHandlerService> QR_CODE_HANDLER_SERVICE_HASH_MAP = new HashMap<>();

    public static QrCodeHandlerService getInstance(int type) {
        return QR_CODE_HANDLER_SERVICE_HASH_MAP.get(String.valueOf(type));
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        Map<String, QrCodeHandlerService> beansOfType = applicationContext.getBeansOfType(QrCodeHandlerService.class);
        for (QrCodeHandlerService value : beansOfType.values()) {
            QR_CODE_HANDLER_SERVICE_HASH_MAP.put(String.valueOf(value.getSupportedType()), value);
        }
    }

}
