package com.dtm.mallproject.handler;

import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.vo.CommentPageDisplayVO;
import com.dtm.mallproject.service.CommentService;
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
 * @date : Created in 2021/12/11 17:37
 * @description : 评论的相关操作对应的处理器
 */
@Api(tags = "CommentHandler-评论的相关操作对应的处理器")
@Controller
@RequestMapping("/comment")
public class CommentHandler {
    @Resource
    private CommentService commentService;

    /**
     * 根据图书编号分页查询评论
     * @param bookId 图书编号
     * @param currentPage 当前页
     * @param pageSize 页面大小
     * @return 分页后的评论
     */
    @ApiOperation("根据图书编号分页查询评论")
    @GetMapping("/selectCommentByBookId/{bookId}/{currentPage}/{pageSize}")
    @ResponseBody
    public CommentPageDisplayVO selectCommentByBookId(
            @ApiParam("图书编号") @PathVariable String bookId,
            @ApiParam("当前页") @PathVariable Integer currentPage,
            @ApiParam("页面大小") @PathVariable Integer pageSize){
        return commentService.selectCommentByBookId(bookId,currentPage,pageSize);
    }

    /**
     * 评论操作
     * @param comments 评论
     * @return 操作结果
     */
    @ApiOperation("评论操作")
    @PostMapping("/insertComment")
    @ResponseBody
    public Integer insertComment(
            @ApiParam("评论") @RequestBody List<CommentDO> comments){
        return commentService.insertComment(comments);
    }

}
