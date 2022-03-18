package com.dtm.mallproject.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.vo.*;

import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/26 16:56
 * @description : 图书的相关操作对应的接口，继承 IService 接口，以实现批量更新操作
 */
public interface BookService extends IService<BookDO> {
    /**
     * 查询所有图书
     * @return 查询结果
     */
    List<BookDO> selectAllBook();

    /**
     * 根据分类分页查询图书
     * @param classification 分类代码
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return DTO对象, 包含图书查询结果和总记录数及总页数信息
     */
    BookPageDisplayVo selectBookByClassification(String classification, Integer currentPage, Integer pageSize);

    /**
     * 根据状态查询图书
     * @param state 状态代码
     * @return 查询结果 (限制首页只显示十本, 就算查询结果超过十条, 也只返回前十条)
     */
    List<BookStateVO> selectBookByState(Integer state);

    /**
     * 根据关键字模糊查询图书
     * @param condition 查询条件，包括关键字、范围、分类、当前页、页面大小
     * @return DTO对象, 包含图书查询结果和总记录数及总页数信息
     */
    BookPageDisplayVo selectBookByKeyword(BookFuzzySearchVO condition);

    /**
     * 根据图书编号查询图书信息
     * @param id 图书编号
     * @return 图书信息
     */
    BookDetailVO selectBookById(String id);

    /**
     * 初始化图书在 Redis 中的库存信息
     */
    void initInventory();

    /**
     * 分页查询图书信息
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 处理后的图书信息
     */
    BookInfoPageDisplayVO selectBookInfo(Integer currentPage, Integer pageSize);

    /**
     * 模糊查询图书信息
     * @param keyWord 关键字
     * @param select 查询范围
     * @return 图书信息
     */
    List<BookDO> searchBookInfo(String keyWord, String select);

    /**
     * 根据图书编号删除图书
     * @param id 图书编号
     * @return 操作结果
     */
    Integer deleteBookById(String id);

    /**
     * 根据图书编号查询图书信息
     * @param id 书编号
     * @return 图书信息
     */
    BookDO selectBookInfoById(String id);

    /**
     * 更新图书信息
     * @param book 图书信息
     * @return 操作结果
     */
     Integer updateBookInfo(BookDO book);

    /**
     * 添加图书
     * @param book 图书信息
     * @return 操作结果
     */
     Integer addBook(BookDO book);

    /**
     * 查询图书排行榜
     * @return 图书排行榜
     */
    List<BookRankVO> selectRankBook();

    /**
     * 根据图书编号查询同类推荐的书
     * @param bookId 图书编号
     * @return 同类推荐的书
     */
    List<RecommendBookVO> selectRecommendBook(String bookId);

    /**
     * 根据图书名或图书编号查询图书信息
     * @param condition 图书名或图书编号
     * @return 图书信息
     */
    BookDO selectBookByCondition(String condition);
}
