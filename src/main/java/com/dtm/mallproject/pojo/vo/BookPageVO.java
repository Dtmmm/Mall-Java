package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/30 16:50
 * @description : View Object，书的分页结果，作为 BookPageDisplayVo 的一个成员变量
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookPageVO {
    private String id;
    private String bookName;
    private String author;
    private Double price;
    private String brief;
    private Double discount;
    private String img;
}
