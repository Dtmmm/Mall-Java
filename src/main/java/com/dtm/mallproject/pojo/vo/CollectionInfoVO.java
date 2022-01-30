package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/6 21:16
 * @description : View Object，对应用户收藏夹信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CollectionInfoVO {
    private String bookId;
    private String bookName;
    private Double discountPrice;
    private Double discount;
    private Double price;
    private String author;
    private String press;
    private String img;
}
