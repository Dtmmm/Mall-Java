package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/1/28 16:05
 * @description : View Object，对应图书详情中同类推荐的书
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class RecommendBookVO {
    private String id;
    private String bookName;
    private Double price;
    private Double discount;
    private String img;
}
