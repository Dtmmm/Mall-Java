package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.entity.BookDO;
import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.BookInfoPageDisplayVO;
import com.dtm.mallproject.pojo.vo.CommentPageDisplayVO;
import com.dtm.mallproject.pojo.vo.UserInfoPageDisplayVO;
import com.dtm.mallproject.service.BookService;
import com.dtm.mallproject.service.CommentService;
import com.dtm.mallproject.service.UserService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/14 14:37
 * @description : 后台管理系统处理器
 */
@Controller
@RequestMapping("/backstage")
public class BackstageHandler {
    @Resource
    private BookService bookService;
    @Resource
    private UserService userService;
    @Resource
    private CommentService commentService;

    /**
     * 分页查询图书信息
     *
     * @return 所有图书
     */
    @GetMapping("/selectBookInfo/{currentPage}/{pageSize}")
    @ResponseBody
    public BookInfoPageDisplayVO selectBookInfo(@PathVariable Integer currentPage,
                                                @PathVariable Integer pageSize) {
        return bookService.selectBookInfo(currentPage, pageSize);
    }

    /**
     * 模糊搜索图书信息
     *
     * @param keyWord 关键字
     * @param select  搜索范围
     * @return 图书信息
     */
    @GetMapping("/searchBookInfo/{keyWord}/{select}")
    @ResponseBody
    public List<BookDO> searchBookInfo(@PathVariable String keyWord,
                                       @PathVariable String select) {
        return bookService.searchBookInfo(keyWord, select);
    }

    /**
     * 根据图书编号删除图书
     *
     * @param id 图书编号
     * @return 操作结果
     */
    @PostMapping("/deleteBookById")
    @ResponseBody
    public Integer deleteBookById(@RequestParam String id) {
        return bookService.deleteBookById(id);
    }

    /**
     * 根据图书编号查询图书信息
     *
     * @param id 图书编号
     * @return 图书信息
     */
    @GetMapping("/selectBookInfoById/{id}")
    @ResponseBody
    public BookDO selectBookInfoById(@PathVariable String id) {
        return bookService.selectBookInfoById(id);
    }

    /**
     * 更新图书信息
     *
     * @param book 图书信息
     * @return 操作结果
     */
    @PutMapping("/updateBookInfo")
    @ResponseBody
    public Integer updateBookInfo(@RequestBody BookDO book) {
        return bookService.updateBookInfo(book);
    }

    /**
     * 添加图书
     *
     * @param book 图书信息
     * @return 操作结果
     */
    @PostMapping("/addBook")
    @ResponseBody
    public Integer addBook(@RequestBody BookDO book){
        return bookService.addBook(book);
    }

    /**
     * 根据图书名或图书编号查询图书信息
     * @param condition 图书名或图书编号
     * @return 图书信息
     */
    @GetMapping("/selectBookByCondition/{condition}")
    @ResponseBody
    public BookDO selectBookByCondition(@PathVariable String condition){
        return bookService.selectBookByCondition(condition);
    }

    /**
     * 分页获取所有用户信息
     *
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的数据
     */
    @GetMapping("/selectAllUserInfo/{currentPage}/{pageSize}")
    @ResponseBody
    public UserInfoPageDisplayVO selectAllUserInfo(@PathVariable Integer currentPage,
                                                   @PathVariable Integer pageSize) {
        return userService.selectAllUserInfo(currentPage,pageSize);
    }

    /**
     * 模糊搜索用户信息
     *
     * @param keyWord 关键字
     * @param select  搜索范围
     * @return 用户信息
     */
    @GetMapping("/searchUserInfo/{keyWord}/{select}")
    @ResponseBody
    public List<UserDO> searchUserInfo(@PathVariable String keyWord,
                                       @PathVariable String select) {
        return userService.searchUserInfo(keyWord,select);
    }

    /**
     * 根据用户编号删除用户
     *
     * @param id 用户编号
     * @return 操作结果
     */
    @PostMapping("/deleteUserById")
    @ResponseBody
    public Integer deleteUserById(@RequestParam String id){
        return userService.deleteUserById(id);
    }

    /**
     * 根据用户名或用户编号查询用户信息
     * @param condition 用户名或用户编号
     * @return 用户信息
     */
    @GetMapping("selectUserByCondition/{condition}")
    @ResponseBody
    public UserDO selectUserByCondition(@PathVariable String condition){
        return userService.selectUserByCondition(condition);
    }

    /**
     * 分页获取所有评论
     *
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的评论信息
     */
    @GetMapping("/selectAllComment/{currentPage}/{pageSize}")
    @ResponseBody
    public CommentPageDisplayVO selectAllComment(@PathVariable Integer currentPage, @PathVariable Integer pageSize){
        return commentService.selectAllComment(currentPage,pageSize);
    }

    /**
     * 根据编号删除评论
     *
     * @param id 评论编号
     * @return 操作结果
     */
    @DeleteMapping("/deleteCommentById/{id}")
    @ResponseBody
    public Integer deleteCommentById(@PathVariable String id){
        return commentService.deleteCommentById(id);
    }

    /**
     * 模糊搜索评论
     *
     * @param keyWord 关键字
     * @param select 搜索范围
     * @return 评论
     */
    @GetMapping("/searchComment/{keyWord}/{select}")
    @ResponseBody
    public List<CommentDO> searchComment(@PathVariable String keyWord,
                                         @PathVariable String select){
        return commentService.searchComment(keyWord,select);
    }
}
