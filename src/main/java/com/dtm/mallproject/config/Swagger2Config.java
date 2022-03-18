package com.dtm.mallproject.config;

import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Controller;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/17 11:17
 * @description : Swagger2 的配置类
 */
@Component
public class Swagger2Config {
    /**
     * 添加摘要信息(Docket)
     */
    @Bean
    public Docket createApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(apiInfo())
                .select()
                // 只扫描该包下的类
                .apis(RequestHandlerSelectors.basePackage("com.dtm.mallproject.handler"))
                .paths(PathSelectors.any())
                .build();
    }

    /**
     * api基本信息的配置
     * @return 基本信息的配置
     */
    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("DengDengPlus 网上图书商城")
                .description("DengDengPlus 网上图书商城项目的 Swagger2 接口")
                .version("版本号：1.0")
                .build();
    }
}
