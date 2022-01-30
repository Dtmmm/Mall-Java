package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:24
 * @description : 订单表对应实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("deal")
public class DealDO {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String userId;
    private String shippingAddress;
    private Double total;
    private String payWay;
    /**
     * 交易状态： 1-交易完成 2-尚未发货 3-已发货 4-已签收 5-待支付 0-售后中
     */
    private Integer state;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;
}
