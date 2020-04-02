package com.mufeng.controller;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import springfox.documentation.annotations.ApiIgnore;

/**
 * @description:
 * @Author: my.yang
 * @Date: 2020/3/26 10:47 PM
 */
@RestController
// 忽略当前Controller下所有Api
@ApiIgnore
public class HelloController {

    @GetMapping("/hello")
    public Object hello() {
        return "Hello World!";
    }
}
