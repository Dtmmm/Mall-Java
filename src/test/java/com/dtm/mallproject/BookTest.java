package com.dtm.mallproject;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtm.mallproject.mapper.BookMapper;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.vo.BookDetailVO;
import com.dtm.mallproject.pojo.vo.BookInfoPageDisplayVO;
import com.dtm.mallproject.service.BookService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.stream.Collectors;

@SpringBootTest
class BookTest {
    @Resource
    private BookMapper bookMapper;
    @Resource
    private BookService bookService;

    // 查询所有图书
    @Test
    void testSelectAllBooks(){
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        List<BookDO> books = bookMapper.selectList(qw);
        books.forEach(System.out::println);
    }

    // 根据分类分页查询
    @Test
    void testSelectBooksByClassification(){
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.likeRight("classification_code","2");
        Page<BookDO> page = new Page<>();
        page.setCurrent(1).setSize(10);
        Page<BookDO> result = bookMapper.selectPage(page, qw);
        List<BookDO> books = result.getRecords();
        books.forEach(System.out::println);
    }

    // 根据状态查询
    @Test
    void testSelectBookByState(){
        QueryWrapper<BookDO> qw = new QueryWrapper<>();
        qw.eq("state",1);
        List<BookDO> books = bookMapper.selectList(qw);
        // 使用流截取前十条数据
        List<BookDO> resultBooks = books.stream().limit(10).collect(Collectors.toList());
        resultBooks.forEach(System.out::println);
    }

    // 根据图书编号查询
    @Test
    void testSelectBookByBookID(){
        BookDetailVO book = bookService.selectBookById("001");
        System.out.println(book);
    }

    @Test
    void testInit(){
        bookService.initInventory();
    }

    @Test
    void testSelectBookInfo(){
        BookInfoPageDisplayVO bookInfoPageDisplayVO = bookService.selectBookInfo(1, 10);
        System.out.println(bookInfoPageDisplayVO);
    }
}
