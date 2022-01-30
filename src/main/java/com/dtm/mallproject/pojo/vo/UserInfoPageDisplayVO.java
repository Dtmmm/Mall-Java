package com.dtm.mallproject.pojo.vo;

import com.dtm.mallproject.pojo.entity.UserDO;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/15 20:52
 * @description : View Object，后台管理系统分页展示用户信息时传递的参数，
 *                            包含用户的分页结果集合以及分页总页数等信息
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class UserInfoPageDisplayVO {
    private List<UserDO> users;
    private Long total;
    private Long pages;
}
