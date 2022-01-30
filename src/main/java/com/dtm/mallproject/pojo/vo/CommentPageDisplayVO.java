package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/11 20:55
 * @description : View Object，前端分页展示评论时传递的参数，包含评论集合以及分页总页数等信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageDisplayVO {
    /**
     * 分页后的评论
     */
    private List<CommentPageVO> comments;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
}
