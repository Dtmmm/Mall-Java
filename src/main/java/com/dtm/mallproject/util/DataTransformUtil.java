package com.dtm.mallproject.util;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtm.mallproject.mapper.ClassificationMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.ClassificationDO;
import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.vo.*;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/30 17:25
 * @description : 组装数据帮助类
 */
@Component
public class DataTransformUtil {
    @Resource
    private ClassificationMapper classificationMapper;

    /**
     * 提取图书分页结果中需要的数据，组装成 BookPageDisplayVo 对象并返回
     *
     * @param page 图书分页结果
     * @return 组装后的 BookPageDisplayVo 对象
     */
    public BookPageDisplayVo pageToBookPageDisplayVo(Page<BookDO> page){
        List<BookDO> books = page.getRecords();
        List<BookPageVO> data = new ArrayList<>();
        books.forEach(
                bookDO -> data.add(new BookPageVO(bookDO.getId(),
                        bookDO.getBookName(), bookDO.getAuthor(),bookDO.getPrice(),
                        bookDO.getBrief(),bookDO.getDiscount(),bookDO.getImg()))
        );
        return new BookPageDisplayVo(data,page.getTotal(),page.getPages());
    }

    /**
     * 提取 BookDO 对象中需要的数据，组装成 BookStateVO 对象并返回
     *
     * @param list BookDO 对象集合
     * @return BookStateVO 对象集合
     */
    @SuppressWarnings("AlibabaLowerCamelCaseVariableNaming")
    public List<BookStateVO> bookDOToBookStateVO(List<BookDO> list){
        List<BookStateVO> data = new ArrayList<>(12);
        // 使用流截取前12条记录
        list.stream().limit(12).forEach(book ->
            data.add(new BookStateVO(book.getId(),book.getBookName(),book.getPrice(),
                    book.getDiscount(),book.getBrief(),book.getState(),book.getImg()))
        );
        return data;
    }

    /**
     * 提取评论分页结果中需要的数据，组装成 CommentVO 对象并返回
     *
     * @param page 评论分页结果
     * @return 组装后的 CommentVO 对象
     */
    public CommentPageDisplayVO commentDOToCommentVO(Page<CommentDO> page){
        List<CommentDO> comments = page.getRecords();
        ArrayList<CommentPageVO> data = new ArrayList<>();
        comments.forEach(
                comment -> data.add(new CommentPageVO(comment.getId(),comment.getBookId(),comment.getBookName(),comment.getUserId(),
                        comment.getUserName(),comment.getIcon(),comment.getContent(),
                        comment.getUpdateTime(),comment.getRate()))
        );
        return new CommentPageDisplayVO(data,page.getTotal(),page.getPages());
    }

    /**
     * 提取图书信息分页结果中需要的数据，并取分类代码对应的中文，组装成 BookInfoPageDisplayVO 对象并返回
     *
     * @param page 书信息分页结果
     * @return 组装后的 BookInfoPageDisplayVO 对象
     */
    public BookInfoPageDisplayVO pageToBookInfoPageDisplayVO(Page<BookDO> page){
        List<BookDO> books = page.getRecords();
        List<BookDO> data = new ArrayList<>();

        books.forEach(book -> {
            // 获取该书的分类代码对应的中文
            QueryWrapper<ClassificationDO> qw = new QueryWrapper<>();
            qw.eq("classification_code",book.getClassificationCode());
            ClassificationDO classification = classificationMapper.selectOne(qw);
            // 设置获取到的中文分类
            book.setClassificationCode(classification.getClassificationMean());
            data.add(book);
        });
        return new BookInfoPageDisplayVO(data,page.getTotal(),page.getPages());
    }

    /**
     * 提取 BookDO 对象中需要的数据，组装成 BookRankVO 对象并返回
     *
     * @param list BookDO 对象集合
     * @return BookRankVO 对象集合
     */
    public List<BookRankVO> BookDOToBookRankVO(List<BookDO> list){
        List<BookRankVO> data = new ArrayList<>(5);
        // 使用流截取前5条记录
        list.stream().limit(5).forEach(book -> {
            data.add(new BookRankVO(book.getId(),book.getBookName(), book.getPrice(),
                    book.getDiscount(), book.getSales(), book.getImg()));
        });
        return data;
    }

    /**
     * 提取 BookDO 对象中需要的数据，组装成 RecommendBookVO 对象并返回
     *
     * @param list BookDO 对象集合
     * @return RecommendBookVO 对象集合
     */
    public List<RecommendBookVO> BookDOToRecommendBookVO(List<BookDO> list){
        List<RecommendBookVO> recommendBooks = new ArrayList<>(6);
        list.forEach(book -> {
            recommendBooks.add(new RecommendBookVO(book.getId(), book.getBookName(),
                    book.getPrice(), book.getDiscount(), book.getImg()));
        });
        return recommendBooks;
    }
}
