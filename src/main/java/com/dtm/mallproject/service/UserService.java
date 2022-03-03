package com.dtm.mallproject.service;

import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.*;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/1 16:44
 * @description : 用户的相关操作对应的接口
 */
public interface UserService {
    /**
     * 根据用户编号查询用户信息
     *
     * @param id 用户编号
     * @return UserInfoVO 对象，包含用户的各项信息
     */
    UserInfoVO selectUserById(String id);

    /**
     * 登录方法
     *
     * @param userId 用户账号
     * @param userPwd 用户密码
     * @return UserLoginVO 对象，包含用户的信息以及登录结果
     *      其中登录结果为1表示登录成功，为0表示账号或密码不正确或该用户不存在
     */
    UserLoginVO login(String userId, String userPwd);

    /**
     * 注册
     *
     * @param user 用户信息
     * @return 操作结果
     */
    Integer register(UserDO user);

    /**
     * 修改用户信息
     *
     * @param user 新的用户信息
     * @return 修改结果，1表示修改成功，0表示修改失败
     */
    Integer updateUser(UserDO user);

    /**
     * 修改购物车
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @param quantity 数量
     * @return 操作结果，0:库存不足 1:成功 2:失败
     */
    Integer updateCartInfo(String id, String bookId, Integer quantity);

    /**
     * 获取用户购物车信息
     *
     * @param id 用户编号
     * @return 购物车信息
     */
    List<CartInfoVO> cartInfo(String id);

    /**
     * 从购物车中删除书
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @param quantity 图书数量
     * @return 操作结果，1：删除成功 0：删除失败
     */
    Integer deleteCart(String id, String bookId, Integer quantity);

    /**
     * 添加到收藏夹
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1:成功 0:失败
     */
    Integer addToCollection(String id, String bookId);

    /**
     * 获取用户收藏夹信息
     *
     * @param id 用户编号
     * @return 收藏夹信息
     */
    List<CollectionInfoVO> collectionInfo(String id);

    /**
     * 从收藏夹中删除图书
     *
     * @param id 用户编号
     * @param bookId 图书编号
     * @return 操作结果，1：删除成功 0：删除失败
     */
    Integer deleteCollection(String id, String bookId);

    /**
     * 分页获取所有用户信息
     *
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的数据
     */
    UserInfoPageDisplayVO selectAllUserInfo(Integer currentPage, Integer pageSize);

    /**
     * 模糊搜索用户信息
     *
     * @param keyWord 关键字
     * @param select 范围
     * @return 用户信息
     */
    List<UserDO> searchUserInfo(String keyWord, String select);

    /**
     * 根据用户编号删除用户
     *
     * @param id 用户编号
     * @return 操作结果
     */
    Integer deleteUserById(String id);

    /**
     * 根据用户名或用户编号查询用户信息
     *
     * @param condition 用户名或用户编号
     * @return 用户信息
     */
    UserDO selectUserByCondition(String condition);

    /**
     * 清空游客购物车
     *
     * @param visitorId 游客编号
     * @return 操作结果
     */
    Integer clearVisitorCart(String visitorId);

    /**
     * 游客登录后，更新合并购物车信息，并删除之前的信息
     *
     * @param visitorId 游客编号
     * @param userId 用户编号
     * @return 操作结果
     */
    Integer updateVisitorToUser(String visitorId,String userId);
}
