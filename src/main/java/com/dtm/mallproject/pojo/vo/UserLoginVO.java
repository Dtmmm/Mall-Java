package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 17:11
 * @description : View Object，用户登录模块传的参数
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserLoginVO {
    private String id;
    private String userId;
    private String userName;
    /**
     * 登录结果
     * 1：登录成功
     * 0：登录失败，账号或者密码错误
     */
    private Integer result;
}
