package com.dtm.mallproject.schedule;

import com.baomidou.mybatisplus.core.conditions.update.LambdaUpdateWrapper;
import com.baomidou.mybatisplus.extension.conditions.update.LambdaUpdateChainWrapper;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.service.serviceImpl.BookServiceImpl;
import com.dtm.mallproject.util.RedisUtil;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Map;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/2 8:48
 * @description : 定时任务类
 */
@Component
@EnableScheduling
public class ScheduleTask {
    @Resource
    private RedisUtil redisUtil;

    @Resource
    private BookServiceImpl bookService;

    /**
     * 从第一分钟开始，每一分钟同步一次Redis的库存信息到数据库
     */
    @Scheduled(cron = "0 1/1 * * * ?")
    public void synRedisData(){
        System.out.println("==========开始同步Redis的库存信息==========");
        try {
            // 取到Redis中库存信息的所有键值对集合
            Map<String, String> inventoryInfo = redisUtil.hEntries("inventory");
            ArrayList<BookDO> books = new ArrayList<>();
            inventoryInfo.forEach((bookId,inventory) -> {
                BookDO book = new BookDO();
                book.setId(bookId);
                book.setInventory(Integer.parseInt(inventory));
                books.add(book);
            });
            // 使用MP提供的批量更新方法
            boolean result = bookService.updateBatchById(books);
            if (!result){
                System.out.println("************同步Redis的库存信息失败************");
            }
        } catch (Exception e){
            System.out.println("************同步Redis的库存信息时出错************");
            System.out.println(e.getMessage());
        }
        System.out.println("=================同步结束=================");
    }
}
