package com.mufeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @description: Swagger配置工具类
 * @Author: my.yang
 * @Date: 2020/4/2 11:11 PM
 */
@Configuration
@EnableSwagger2
public class Swagger2 {

    // 源路径 : http://localhost:8088/swagger-ui.html
    // 换肤 : http://localhost:8088/doc.html

    /**
     * 配置swagger2核心docket
     *
     * @return
     */
    @Bean
    public Docket CreateRestApi() {
        /**
         * 指定Api类型为swagger2
         */
        return new Docket(DocumentationType.SWAGGER_2)
                // 用户定义Api文档汇总信息
                .apiInfo(apiInfo())
                // 指定Cotroller包
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.mufeng.controller"))
                // 将所有Controller列入
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * Api参数配置
     *
     * @return
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                // 文档页标题
                .title("电商平台接口Api")
                // 联系人信息
                .contact(new Contact("mufeng", "https://www.mufeng.com", "abc@abc.com"))
                // 详细信息
                .description("接口文档Api")
                // 文档版本号
                .version("V1.0")
                // 网站地址
                .termsOfServiceUrl("https://www.abctest.com")
                .build();
    }
}
