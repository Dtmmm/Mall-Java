package com.dtm.mallproject.pojo.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/14 16:42
 * @description : 分类表对应的实体类
 */
@Data
@TableName("classification")
public class ClassificationDO {
    @TableId(value = "id", type = IdType.ASSIGN_UUID)
    private String id;
    private String classificationCode;
    private String classificationMean;
    private String parentClassificationCode;
}
