package com.dtm.mallproject.init;

import com.dtm.mallproject.service.BookService;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/1/30 15:52
 * @description : 在项目启动时，自动调用该方法，进行Redis缓存的初始化
 */
@Component
public class InitRedisData implements ApplicationRunner {
    @Resource
    BookService bookService;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        bookService.initInventory();
    }
}
