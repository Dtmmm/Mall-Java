package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/29 22:20
 * @description : View Object，前端进入图书详情页时传递的参数，包含图书的详细信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookDetailVO {
    private String id;
    private String bookName;
    private String author;
    private Double price;
    private String press;
    private String publicationDate;
    private String brief;
    private Double discount;
    private String img;
}
