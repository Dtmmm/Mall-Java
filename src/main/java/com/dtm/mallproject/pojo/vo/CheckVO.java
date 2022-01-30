package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 19:52
 * @description : View Object，用户结算订单时传递的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CheckVO {
    private String userId;
    private String shippingAddress;
    private Double total;
    private String payWay;
    private Integer state;
}
