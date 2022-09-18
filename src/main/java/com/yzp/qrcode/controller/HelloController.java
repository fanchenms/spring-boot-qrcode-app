package com.yzp.qrcode.controller;

import com.yzp.qrcode.common.PublicConstant;
import com.yzp.qrcode.service.DictService;
import com.yzp.qrcode.utils.QrCodeUtil;
import com.yzp.qrcode.utils.R;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.List;

/**
 * @author yzp
 * @version 1.0
 * @date 2022/9/2 - 0:08
 */
@Slf4j
@RestController
public class HelloController {

    @Resource
    private DictService dictService;

    @GetMapping("hello")
    public R hello() {
        System.out.println(R.error().putData("失败"));
        System.out.println(R.success().putData("成功"));

        String staticResourceDomainName = dictService.getValue(PublicConstant.STATIC_RESOURCE_DOMAIN_NAME);
        log.info("静态资源域名: {}", staticResourceDomainName);

        TestJson testJson = dictService.getDictObject("TEST_JSON", TestJson.class);
        log.info("测试JSON: {}", testJson);

        return R.success().putData("");
    }

    @Data
    static class TestJson {
        // {"name": "小明", "age": 20, "hello": [1,2,3]}
        private String name;
        private Integer age;
        private List<Integer> hello;
    }

    @GetMapping("/qrcode1")
    public void getQRCodeTest1(HttpServletResponse response) throws Exception {
        QrCodeUtil.encode("http://www.baidu.com", response.getOutputStream());
    }

    @GetMapping("/base641")
    public String getQRCodeBase641() throws Exception {
        String base64 = QrCodeUtil.encode("http://www.baidu.com");
        log.info("base64 ==> {}", base64);
        log.info("Base64 ==> {}", "data:image/jpg;base64," + base64);
        return "data:image/jpg;base64," + base64;
    }

    @GetMapping("/redirect")
    public void redirect(HttpServletResponse response) {
        try {
            response.sendRedirect("http://10.198.1.14:8090/base641");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

}
