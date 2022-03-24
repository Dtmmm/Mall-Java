package com.dtm.mallproject.pojo.vo;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Tolerate;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2022/3/23 14:55
 * @description : View Object，导出图书销售信息时传递的参数
 */
@Data
@Builder
public class BookExcelVO {
    @Tolerate
    public BookExcelVO() {
    }

    private String bookId;
    private String bookName;
    private String author;
    private String press;
    private String price;
    private String discount;
    private String state;
    private String classification;
    private Integer[][] salesVolume;
    private Integer[][] collection;
    private Integer[][] clicks;
    private Integer[] rate;
}
