package com.dtm.mallproject.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.dtm.mallproject.exception.EmptyParameterException;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.vo.*;
import com.dtm.mallproject.service.BookService;
import com.dtm.mallproject.util.DataTransformUtil;
import com.dtm.mallproject.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/26 16:57
 * @description : 图书接口实现类
 */
@Slf4j
@Service
public class BookServiceImpl extends ServiceImpl<BookMapper,BookDO> implements BookService {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private RedisUtil redisUtil;
    @Resource
    private DataTransformUtil dataTransformUtil;

    @Override
    public List<BookDO> selectAllBook() {
        return bookMapper.selectList(new QueryWrapper<>());
    }

    @Override
    public BookPageDisplayVo selectBookByClassification(String classification, Integer currentPage, Integer pageSize) {
        // 查询条件
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.likeRight("classification_code", classification);
        // 分页条件
        Page<BookDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<BookDO> result = bookMapper.selectPage(page, qw);

        // 返回组装后的结果
        return dataTransformUtil.pageToBookPageDisplayVo(result);
    }

    @Override
    public List<BookStateVO> selectBookByState(Integer state) {
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.eq("state", state);
        // 返回提取后的结果
        return dataTransformUtil.bookDOToBookStateVO(bookMapper.selectList(qw));
    }

    @Override
    public BookPageDisplayVo selectBookByKeyword(BookFuzzySearchVO condition) {
        String keyWord = condition.getKeyWord();
        List<String> scope = condition.getScope();
        List<String> classification = condition.getClassification();
        int currentPage = condition.getCurrentPage();
        int pageSize = condition.getPageSize();

        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        // 拼接范围查询条件
        try {
            if (scope.size() <= 0) {
                throw new EmptyParameterException("范围不能为空");
            }
            // 这样写可以实现将scope拼接的所有条件用括号括起来
            qw.and(wrapper -> {
                int size = scope.size();
                for (int i = 0; i < size; i++) {
                    wrapper.like(scope.get(i), keyWord);
                    if (i != size - 1) {
                        wrapper.or();
                    }
                }
            });
        } catch (EmptyParameterException e) {
            System.out.println(e.getMessage());
        }

        // 拼接分类查询条件
        if (classification.size() > 0) {
            qw.and(wrapper -> {
                int size = classification.size();
                for (int i = 0; i < size; i++) {
                    wrapper.likeRight("classification_code", classification.get(i));
                    if (i != size - 1) {
                        wrapper.or();
                    }
                }
            });
        }

        Page<BookDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<BookDO> result = bookMapper.selectPage(page, qw);
        // 返回组装后的结果
        return dataTransformUtil.pageToBookPageDisplayVo(result);
    }

    @Override
    public BookDetailVO selectBookById(String id) {
        BookDO book = bookMapper.selectById(id);
        // 组装数据
        return new BookDetailVO(
                book.getId(), book.getBookName(), book.getAuthor(),
                book.getPrice(), book.getPress(), book.getPublicationDate(),
                book.getBrief(), book.getDiscount(), book.getImg());
    }

    @Override
    public void initInventory() {
        redisUtil.del("inventory");
        log.info("===开始初始化库存信息===");
        List<BookDO> books = bookMapper.selectList(new QueryWrapper<>());
        books.forEach(book -> {
            String id = book.getId();
            Integer inventory = book.getInventory();
            redisUtil.hSet("inventory", id, inventory.toString());
        });
        log.info("===初始化库存信息完成===");
    }

    @Override
    public BookInfoPageDisplayVO selectBookInfo(Integer currentPage, Integer pageSize) {
        Page<BookDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<BookDO> result = bookMapper.selectPage(page, new QueryWrapper<>());

        // 返回组装后的结果
        return dataTransformUtil.pageToBookInfoPageDisplayVO(result);
    }

    @Override
    public List<BookDO> searchBookInfo(String keyWord, String select) {
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.like(select, keyWord);
        return bookMapper.selectList(qw);
    }

    @Override
    public Integer deleteBookById(String id) {
        return bookMapper.deleteById(id);
    }

    @Override
    public BookDO selectBookInfoById(String id) {
        return bookMapper.selectById(id);
    }

    @Override
    public Integer updateBookInfo(BookDO book) {
        return bookMapper.updateById(book);
    }

    @Override
    public List<BookRankVO> selectRankBook() {
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.orderByDesc("sales");
        return dataTransformUtil.BookDOToBookRankVO(bookMapper.selectList(qw));
    }

    @Override
    public Integer addBook(BookDO book) {
        return bookMapper.insert(book);
    }

    @Override
    public List<RecommendBookVO> selectRecommendBook(String bookId) {
        // 先根据图书编号查询图书的分类
        BookDO book = bookMapper.selectOne(
                new QueryWrapper<BookDO>().select("classification_code").eq("id",bookId)
        );

        // 再根据分类查询同类图书并排除当前本且只取销量最高的前6本
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.eq("classification_code",book.getClassificationCode())
                .ne("id",bookId)
                .orderByDesc("sales")
                .last("limit 6");
        List<BookDO> books = bookMapper.selectList(qw);

        // 返回组装后的数据
        return dataTransformUtil.BookDOToRecommendBookVO(books);
    }

    @Override
    public BookDO selectBookByCondition(String condition) {
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.eq("id",condition).or().eq("book_name",condition);
        return bookMapper.selectOne(qw);
    }
}
