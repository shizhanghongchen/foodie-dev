package com.mufeng;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import tk.mybatis.spring.annotation.MapperScan;

/**
 * @description:
 * @Author: my.yang
 * @Date: 2020/3/26 10:45 PM
 */
@SpringBootApplication
// 扫描MyBatis通用mapper所在的包
@MapperScan(basePackages = "com.mufeng.mapper")
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(Application.class,args);
    }
}
