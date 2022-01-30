package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/20 14:29
 * @description : View Object，前端查询图书排行时传递的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookRankVO {
    private String id;
    private String bookName;
    private Double price;
    private Double discount;
    private Integer sales;
    private String img;
}
