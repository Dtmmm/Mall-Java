package com.dtm.mallproject.service;

import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.vo.CommentPageDisplayVO;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/11 17:35
 * @description : 评论的相关操作对应的接口
 */
public interface CommentService {
    /**
     * 根据图书编号分页查询评论
     *
     * @param bookId 图书编号
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的评论信息
     */
    CommentPageDisplayVO selectCommentByBookId(String bookId, Integer currentPage, Integer pageSize);

    /**
     * 分页获取所有评论
     *
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的评论信息
     */
    CommentPageDisplayVO selectAllComment(Integer currentPage, Integer pageSize);

    /**
     * 根据编号删除评论
     *
     * @param id 评论编号
     * @return 操作结果
     */
    Integer deleteCommentById(String id);

    /**
     * 模糊搜索评论
     *
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 评论
     */
    List<CommentDO> searchComment(String keyWord, String select);

    /**
     * 评论操作
     *
     * @param comments 评论
     * @return 操作结果
     */
    Integer insertComment(List<CommentDO> comments);
}
