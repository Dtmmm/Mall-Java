package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/17 16:07
 * @description : View Object，前端操作购物车时传递的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UpdateCartVO {
    private String id;
    private String bookId;
    private Integer quantity;
}
