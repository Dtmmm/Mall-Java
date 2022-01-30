package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/30 18:00
 * @description : View Object，前端查询图书是否首页展示时传递的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookStateVO {
    private String id;
    private String bookName;
    private Double price;
    private Double discount;
    private String brief;
    private Integer state;
    private String img;
}
