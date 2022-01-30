package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 16:46
 * @description : View Object，前端查询用户信息时传的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoVO {
    private String userId;
    private String userName;
    private String phoneNumber;
    private String email;
    private String shippingAddress;
    private String icon;
}
