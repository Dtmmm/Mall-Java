package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.vo.*;
import com.dtm.mallproject.service.BookService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/11/22 22:20
 * @description : 图书的相关操作对应的处理器
 */
@Api(tags = "BookHandler-图书的相关操作对应的处理器")
@Controller
@RequestMapping("/book")
public class BookHandler {
    @Resource
    private BookService bookService;

    /**
     * 查询所有图书
     * @return 图书集合
     */
    @ApiOperation("查询所有图书")
    @GetMapping("/selectAllBook")
    @ResponseBody
    public List<BookDO> selectAllBook(){
        return bookService.selectAllBook();
    }

    /**
     * 分类分页查询图书
     * @param classification 类别
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的结果
     */
    @ApiOperation("分类分页查询图书")
    @GetMapping("/selectBook/{classification}/{currentPage}/{pageSize}")
    @ResponseBody
    public BookPageDisplayVo selectBookByClassification(
            @ApiParam("类别") @PathVariable String classification,
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize){
        return bookService.selectBookByClassification(classification, currentPage, pageSize);
    }

    /**
     * 根据状态查询图书
     * @param state 状态代码
     * @return 查询结果 (只返回前十条数据)
     */
    @ApiOperation("根据状态查询图书")
    @GetMapping("/selectBookByState/{state}")
    @ResponseBody
    public List<BookStateVO> selectBookByState(
            @ApiParam("状态代码") @PathVariable String state){
        return bookService.selectBookByState(Integer.parseInt(state));
    }

    /**
     * 根据关键字查询图书
     * @param condition 查询条件，包括关键字、范围、分类、当前页、页面大小
     * @return 查询结果
     */
    @ApiOperation("根据关键字查询图书")
    @PostMapping("/selectBookByKeyWord")
    @ResponseBody
    public BookPageDisplayVo selectBookByKeyWord(
            @ApiParam("查询条件，包括关键字、范围、分类、当前页、页面大小") @RequestBody BookFuzzySearchVO condition){
        return bookService.selectBookByKeyword(condition);
    }

    /**
     * 根据图书编号查询图书详细信息
     * @param bookId 图书编号
     * @return 图书详细信息
     */
    @ApiOperation("根据图书编号查询图书详细信息")
    @GetMapping("/selectBookById/{bookId}")
    @ResponseBody
    public BookDetailVO selectBookById(
            @ApiParam("图书编号") @PathVariable String bookId){
        return bookService.selectBookById(bookId);
    }

    /**
     * 查询图书排行榜
     * @return 图书排行榜
     */
    @ApiOperation("查询图书排行榜")
    @GetMapping("/selectRankBook")
    @ResponseBody
    public List<BookRankVO> selectRankBook(){
        return bookService.selectRankBook();
    }

    /**
     * 根据图书编号查询同类图书
     * @param bookId 图书编号
     * @return 同类图书
     */
    @ApiOperation("根据图书编号查询同类图书")
    @GetMapping("/selectRecommendBook/{bookId}")
    @ResponseBody
    public List<RecommendBookVO> selectRecommendBook(
            @ApiParam("图书编号") @PathVariable String bookId){
        return bookService.selectRecommendBook(bookId);
    }

}