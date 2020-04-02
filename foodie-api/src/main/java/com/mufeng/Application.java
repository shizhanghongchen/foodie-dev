package com.mufeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.stereotype.Component;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @description:
 * @EnableTransactionManagement : 事物的引入是通过自动装配引入的;
 * org.springframework.boot.autoconfigure.transaction.TransactionAutoConfiguration (aop方式引入)
 * 1. JdkDynamicAutoProxyConfiguration
 * 2. CglibAutoProxyConfiguration
 * @Author: my.yang
 * @Date: 2020/3/26 10:45 PM
 */
@SpringBootApplication
// 扫描MyBatis通用mapper所在的包
@MapperScan(basePackages = "com.mufeng.mapper")
// 扫描所有包已经相关包
@ComponentScan(basePackages = {"com.mufeng","org.n3r.idworker"})
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class, args);
    }
}
