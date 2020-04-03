package com.mufeng.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.cors.CorsConfiguration;
import org.springframework.web.cors.UrlBasedCorsConfigurationSource;
import org.springframework.web.filter.CorsFilter;

/**
 * @description: 跨域请求配置
 * @Author: my.yang
 * @Date: 2020/4/3 2:08 PM
 */
@Configuration
public class CorsConfig {

    public CorsConfig() {
    }

    @Bean
    public CorsFilter corsFilter() {
        // 1. 添加cors配置信息
        CorsConfiguration config = new CorsConfiguration();
        // 2. 设置请求域
        config.addAllowedOrigin("http://localhost:8080");
        // 3. 设置是否发送Cookie信息
        config.setAllowCredentials(true);
        // 4. 设置允许的请求方式
        config.addAllowedMethod("*");
        // 5. 设置允许的header
        config.addAllowedHeader("*");
        // 6. 为URL添加映射的路径
        UrlBasedCorsConfigurationSource corsSource = new UrlBasedCorsConfigurationSource();
        corsSource.registerCorsConfiguration("/**", config);
        // 7. 返回重新定义好的corsSource
        return new CorsFilter(corsSource);
    }
}
