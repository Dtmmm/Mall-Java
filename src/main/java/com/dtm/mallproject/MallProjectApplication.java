package com.dtm.mallproject;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/22 19:41
 * @description : SpringBoot启动类
 */
@SpringBootApplication
@MapperScan("com.dtm.mallproject.mapper")
public class MallProjectApplication {

    public static void main(String[] args) {
        SpringApplication.run(MallProjectApplication.class, args);
    }

}
