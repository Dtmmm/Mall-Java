package com.dtm.mallproject;

import com.dtm.mallproject.pojo.vo.CommentPageDisplayVO;
import com.dtm.mallproject.service.CommentService;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;

@SpringBootTest
class CommentTest {
    @Resource
    CommentService commentService;

    @Test
    void testSelectComment(){
        CommentPageDisplayVO commentPageDisplayVO = commentService.selectCommentByBookId("001",1,10);
        System.out.println(commentPageDisplayVO);
    }
}
