package com.dtm.mallproject.handler;

import com.dtm.mallproject.exception.WorkBookErrorException;
import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.*;
import com.dtm.mallproject.service.BookService;
import com.dtm.mallproject.service.CommentService;
import com.dtm.mallproject.service.UserService;
import com.dtm.mallproject.util.ExcelUtil;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/14 14:37
 * @description : 后台管理系统处理器
 */
@Api(tags = "BackstageHandler-后台管理系统处理器")
@Slf4j
@RestController
@RequestMapping("/backstage")
public class BackstageHandler {
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;
    @Resource
    private ExcelUtil excelUtil;

    /**
     * 分页查询图书信息
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 图书信息
     */
    @ApiOperation("分页查询图书信息")
    @GetMapping("/selectBookInfo/{currentPage}/{pageSize}")
    public BookInfoPageDisplayVO selectBookInfo(
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize) {
        return bookService.selectBookInfo(currentPage, pageSize);
    }

    /**
     * 模糊搜索图书信息
     * @param keyWord 关键字
     * @param select  搜索范围
     * @return 图书信息
     */
    @ApiOperation("模糊搜索图书信息")
    @GetMapping("/searchBookInfo/{keyWord}/{select}")
    public List<BookDO> searchBookInfo(
            @ApiParam("关键字") @PathVariable String keyWord,
            @ApiParam("搜索范围") @PathVariable String select) {
        return bookService.searchBookInfo(keyWord, select);
    }

    /**
     * 根据图书编号删除图书
     * @param bookId 图书编号
     * @return 操作结果
     */
    @ApiOperation("根据图书编号删除图书")
    @DeleteMapping("/deleteBookById/{bookId}")
    public Integer deleteBookById(@ApiParam("图书编号") @PathVariable String bookId) {
        return bookService.deleteBookById(bookId);
    }

    /**
     * 根据图书编号查询图书信息
     * @param id 图书编号
     * @return 图书信息
     */
    @ApiOperation("根据图书编号查询图书信息")
    @GetMapping("/selectBookInfoById/{id}")
    public BookDO selectBookInfoById(@ApiParam("图书编号") @PathVariable String id) {
        return bookService.selectBookInfoById(id);
    }

    /**
     * 更新图书信息
     * @param book 图书信息
     * @return 操作结果
     */
    @ApiOperation("更新图书信息")
    @PutMapping("/updateBookInfo")
    public Integer updateBookInfo(@ApiParam("图书信息") @RequestBody BookDO book) {
        return bookService.updateBookInfo(book);
    }

    /**
     * 添加图书
     * @param book 图书信息
     * @return 操作结果
     */
    @ApiOperation("添加图书")
    @PostMapping("/addBook")
    public Integer addBook(@ApiParam("图书信息") @RequestBody BookDO book){
        return bookService.addBook(book);
    }

    /**
     * 根据图书名或图书编号查询图书信息
     * @param condition 图书名或图书编号
     * @return 图书信息
     */
    @ApiOperation("根据图书名或图书编号查询图书信息")
    @GetMapping("/selectBookByCondition/{condition}")
    public BookDO selectBookByCondition(@ApiParam("图书名或图书编号") @PathVariable String condition){
        return bookService.selectBookByCondition(condition);
    }

    /**
     * 分页获取所有用户信息
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的数据
     */
    @ApiOperation("分页获取所有用户信息")
    @GetMapping("/selectAllUserInfo/{currentPage}/{pageSize}")
    public UserInfoPageDisplayVO selectAllUserInfo(
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize) {
        return userService.selectAllUserInfo(currentPage,pageSize);
    }

    /**
     * 模糊搜索用户信息
     * @param keyWord 关键字
     * @param select  搜索范围
     * @return 用户信息
     */
    @ApiOperation("模糊搜索用户信息")
    @GetMapping("/searchUserInfo/{keyWord}/{select}")
    public List<UserDO> searchUserInfo(
            @ApiParam("关键字") @PathVariable String keyWord,
            @ApiParam("搜索范围") @PathVariable String select) {
        return userService.searchUserInfo(keyWord,select);
    }

    /**
     * 根据用户编号删除用户
     * @param userId 用户编号
     * @return 操作结果
     */
    @ApiOperation("根据用户编号删除用户")
    @DeleteMapping("/deleteUserById/{userId}")
    public Integer deleteUserById(@ApiParam("用户编号") @PathVariable String userId){
        return userService.deleteUserById(userId);
    }

    /**
     * 根据用户名或用户编号查询用户信息
     * @param condition 用户名或用户编号
     * @return 用户信息
     */
    @ApiOperation("根据用户名或用户编号查询用户信息")
    @GetMapping("selectUserByCondition/{condition}")
    public UserDO selectUserByCondition(
            @ApiParam("用户名或用户编号") @PathVariable String condition){
        return userService.selectUserByCondition(condition);
    }

    /**
     * 分页获取所有评论
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的评论信息
     */
    @ApiOperation("分页获取所有评论")
    @GetMapping("/selectAllComment/{currentPage}/{pageSize}")
    public CommentPageDisplayVO selectAllComment(
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize){
        return commentService.selectAllComment(currentPage,pageSize);
    }

    /**
     * 根据编号删除评论
     * @param id 评论编号
     * @return 操作结果
     */
    @ApiOperation("根据编号删除评论")
    @DeleteMapping("/deleteCommentById/{id}")
    public Integer deleteCommentById(@ApiParam("评论编号") @PathVariable String id){
        return commentService.deleteCommentById(id);
    }

    /**
     * 模糊搜索评论
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 评论
     */
    @ApiOperation("模糊搜索评论")
    @GetMapping("/searchComment/{keyWord}/{select}")
    public List<CommentDO> searchComment(
            @ApiParam("关键字") @PathVariable String keyWord,
            @ApiParam("搜索范围") @PathVariable String select){
        return commentService.searchComment(keyWord,select);
    }

    /**
     * 导出图书销售情况
     * @param response 响应
     * @param bookExcelVO 图书数据
     */
    @ApiOperation("导出图书销售情况")
    @PostMapping("/exportBookExcel")
    public void exportBookExcel(
            @ApiParam("响应") HttpServletResponse response,
            @ApiParam("图书数据") @RequestBody BookExcelVO bookExcelVO){
        Workbook workbook = null;
        try {
            workbook = excelUtil.exportBookExcel(bookExcelVO);
            if (workbook == null){
                throw new WorkBookErrorException("WorkBook为空");
            }
        } catch (WorkBookErrorException e){
            log.error("****获取 WorkBook 失败****");
            System.out.println(e.getMessage());
        }

        String fileName = new String("图书销售情况.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    /**
     * 导出用户消费情况
     * @param response 响应
     * @param userExcelVO 用户数据
     */
    @ApiOperation("导出用户消费情况")
    @PostMapping("/exportUserExcel")
    public void exportUserExcel(
            @ApiParam("响应") HttpServletResponse response,
            @ApiParam("用户数据") @RequestBody UserExcelVO userExcelVO){
        Workbook workbook = null;
        try {
            workbook = excelUtil.exportUserExcel(userExcelVO);
            if (workbook == null){
                throw new WorkBookErrorException("WorkBook为空");
            }
        } catch (WorkBookErrorException e){
            log.error("****获取 WorkBook 失败****");
            System.out.println(e.getMessage());
        }

        String fileName = new String("用户消费情况.xlsx".getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        response.addHeader("Content-Disposition", "attachment;filename=" + fileName);
        response.setContentType("application/octet-stream");

        OutputStream os = null;
        try {
            os = response.getOutputStream();
            workbook.write(os);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (os != null){
                try {
                    os.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }
}
