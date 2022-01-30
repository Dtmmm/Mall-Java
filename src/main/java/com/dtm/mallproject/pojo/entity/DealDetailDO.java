package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/7 17:28
 * @description : 订单明细表对应的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("deal_detail")
public class DealDetailDO {
    @TableId(value = "id", type = IdType.AUTO)
    private String id;
    private String dealId;
    private String bookId;
    private String bookName;
    private String bookPrice;
    private Integer bookQuantity;
    private String bookImg;
}
