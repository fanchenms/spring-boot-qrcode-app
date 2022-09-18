package com.yzp.qrcode;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class SpringBootQrcodeAppApplication {

    public static void main(String[] args) {
        SpringApplication.run(SpringBootQrcodeAppApplication.class, args);
    }

}
