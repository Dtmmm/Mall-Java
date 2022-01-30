package com.dtm.mallproject.pojo.vo;

import com.dtm.mallproject.pojo.entity.BookDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/14 16:26
 * @description : View Object，后台管理系统分页展示图书信息时传递的参数，
 *                          包含书的分页结果集合以及分页总页数等信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookInfoPageDisplayVO {
    /**
     * 分页后的书
     */
    private List<BookDO> books;
    /**
     * 总记录数
     */
    private Long total;
    /**
     * 总页数
     */
    private Long pages;
}
