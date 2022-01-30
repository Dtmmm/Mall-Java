package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/20 16:43
 * @description : View Object，后台管理系统分页展示订单信息时传递的参数，
 *                           包含订单的分页结果集合以及分页总页数等信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DealInfoPageDisplayVO {
    private List<DealInfoVO> deals;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
}
