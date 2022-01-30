package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/2 0:09
 * @description : View Object，前端修改用户信息时传的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserUpdateVO {
    private String userId;
    private String userName;
    private String phoneNumber;
    private String email;
    private String shippingAddress;
    /**
     * 修改结果
     * 1：修改成功
     * 0：修改失败
     */
    private Integer result;
}
