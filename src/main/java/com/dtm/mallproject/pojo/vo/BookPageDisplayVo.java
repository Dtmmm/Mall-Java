package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/23 20:40
 * @description : View Object，前端分页展示图书缩略信息时传递的参数，
 *            包含书的分页结果集合以及分页总页数等信息
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class BookPageDisplayVo {
    /**
     * 分页后的书
     */
    private List<BookPageVO> books;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
}
