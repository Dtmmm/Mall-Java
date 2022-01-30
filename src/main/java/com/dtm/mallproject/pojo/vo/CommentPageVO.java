package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/11 17:58
 * @description : View Object，对应图书评论信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class CommentPageVO {
    private Integer id;
    private String bookId;
    private String bookName;
    private String userId;
    private String userName;
    private String icon;
    private String content;
    private LocalDateTime updateTime;
    private Integer rate;
}
