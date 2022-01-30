package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 15:42
 * @description : 用户表对应的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("user")
public class UserDO {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String userId;
    private String userPwd;
    private String userName;
    private String phoneNumber;
    private String email;
    private String shippingAddress;
    private String icon;
    /**
     * 格式化后的 LocalDateTime 类型时间变成 String 型了
     */
    private String lastLoginDate;
    private Double consumption;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;
}
