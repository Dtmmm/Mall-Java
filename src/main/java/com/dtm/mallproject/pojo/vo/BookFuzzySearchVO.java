package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/27 15:33
 * @description : View Object, 前端模糊搜索时传递的参数,
 *          包含字符型型关键字, 集合型范围, 数值型当前页, 数值型页面大小
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class BookFuzzySearchVO {
    private String keyWord;
    private List<String> scope;
    private List<String> classification;
    private Integer currentPage;
    private Integer pageSize;
}
