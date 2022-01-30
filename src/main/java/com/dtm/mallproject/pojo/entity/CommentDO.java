package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/11 17:29
 * @description : 评论表对应的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("comment")
public class CommentDO {
    @TableId(value = "id", type = IdType.AUTO)
    private Integer id;
    private String bookId;
    private String bookName;
    private String userId;
    private String userName;
    private String icon;
    private String content;
    private Integer rate;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;
}
