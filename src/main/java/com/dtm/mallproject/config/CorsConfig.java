package com.dtm.mallproject.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/22 22:39
 * @description : 解决跨域问题
 */
@Configuration
public class CorsConfig implements WebMvcConfigurer{
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOriginPatterns("*")
                .allowedMethods("GET","HEAD","DELETE","POST","PUT")
                .allowCredentials(true)
                .maxAge(3600)
                .allowedHeaders("*");
    }
}
