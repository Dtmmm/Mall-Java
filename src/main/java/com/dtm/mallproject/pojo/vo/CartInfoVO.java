package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/5 14:47
 * @description : View Object，对应用户购物车信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CartInfoVO {
    private String bookId;
    private String bookName;
    /**
     * 该价格为折扣后的价格
     */
    private Double discountPrice;
    private String author;
    private String press;
    private Integer quantity;
    private String img;
}
