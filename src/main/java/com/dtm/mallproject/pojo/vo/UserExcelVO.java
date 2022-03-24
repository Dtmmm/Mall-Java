package com.dtm.mallproject.pojo.vo;

import lombok.Builder;
import lombok.Data;
import lombok.experimental.Tolerate;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/24 10:37
 * @description : View Object，导出用户消费信息时传递的参数
 */
@Data
@Builder
public class UserExcelVO {
    @Tolerate
    public UserExcelVO() {
    }

    private String userId;
    private String userName;
    private String shippingAddress;
    private String phoneNumber;
    private String email;
    private Double consumption;
    private String lastLoginDate;
    private Integer[][] pageviews;
    private Integer[][] consumptions;
    private Integer[] metrics;
}
