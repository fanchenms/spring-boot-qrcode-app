package com.yzp.qrcode.config;

import com.github.xiaoymin.swaggerbootstrapui.annotations.EnableSwaggerBootstrapUI;
import com.google.common.collect.Lists;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.service.Parameter;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.List;

/**
 * swagger 文档
 * 访问：http://localhost:8090/doc.html
 * swagger-bootstrap-ui默认的文档访问地址是dom.html
 *
 * springfox-swagger-ui：
 * swagger ： http://localhost:8090/swagger/index.html
 * springboot中的swagger：http://localhost:8090/swagger-ui.html
 *
 * @author yzp
 * @version 1.0
 * @date 2022/9/4 - 21:44
 */
@Configuration
@EnableSwagger2
@EnableSwaggerBootstrapUI
public class SwaggerConfig {

    private static final String NAME = "qrcode";
    private static final String URL = "";
    private static final String EMAIL = "";

    /**
     * Swagger请求头
     */
    private static List<Parameter> SWAGGER_HEADERS = Lists.newLinkedList();

    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("qrcode")
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.yzp.qrcode.controller"))
                .paths(PathSelectors.any())
                .build().apiInfo(apiInfo()).globalOperationParameters(SWAGGER_HEADERS);
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder().title("二维码服务API接口文档")
                .contact(new Contact(NAME, URL, EMAIL))
                .version("1.0").description("App接口，所有header请求如果是中文，请先URLEncoder").build();
    }

//    @Bean
//    public Docket createRestApiWebClient() {
//        return new Docket(DocumentationType.SWAGGER_2)
//                .groupName("qrcode2")
//                .select()
//                .apis(RequestHandlerSelectors.basePackage("com.yzp.qrcode.webcontroller"))
//                .paths(PathSelectors.any())
//                .build().apiInfo(apiInfo()).globalOperationParameters(SWAGGER_HEADERS);
//    }

}
