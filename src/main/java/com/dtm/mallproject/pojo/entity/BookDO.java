package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/26 16:33
 * @description : 图书表对应的实体类
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@TableName("book")
public class BookDO {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String bookName;
    private String author;
    private Double price;
    private String press;
    private String publicationDate;
    private Integer inventory;
    private Integer sales;
    private String brief;
    private Integer state;
    private Double discount;
    private String classificationCode;
    private String img;
    private String isbn;
    @TableField(fill = FieldFill.INSERT)
    private LocalDateTime createTime;
    @TableField(fill = FieldFill.INSERT_UPDATE)
    private LocalDateTime updateTime;
    private Integer deleted;
}
