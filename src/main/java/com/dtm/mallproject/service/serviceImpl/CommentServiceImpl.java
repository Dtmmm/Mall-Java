package com.dtm.mallproject.service.serviceImpl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.dtm.mallproject.mapper.CommentMapper;
import com.dtm.mallproject.mapper.UserMapper;
import com.dtm.mallproject.pojo.entity.CommentDO;
import com.dtm.mallproject.pojo.entity.UserDO;
import com.dtm.mallproject.pojo.vo.CommentPageDisplayVO;
import com.dtm.mallproject.service.CommentService;
import com.dtm.mallproject.util.DataTransformUtil;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @author : 邓童淼
 * @program : Mall-Project
 * @date : Created in 2021/12/11 17:36
 * @description : 评论接口实现类
 */
@Service
public class CommentServiceImpl implements CommentService {
    @Resource
    CommentMapper commentMapper;
    @Resource
    DataTransformUtil dataTransformUtil;
    @Resource
    UserMapper userMapper;

    @Override
    public CommentPageDisplayVO selectCommentByBookId(String bookId, Integer currentPage, Integer pageSize) {
        QueryWrapper<CommentDO> qw = new QueryWrapper<>();
        qw.eq("book_id",bookId);
        Page<CommentDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<CommentDO> result = commentMapper.selectPage(page, qw);
        return dataTransformUtil.commentDOToCommentVO(result);
    }

    @Override
    public CommentPageDisplayVO selectAllComment(Integer currentPage, Integer pageSize) {
        Page<CommentDO> page = new Page<>();
        page.setCurrent(currentPage).setSize(pageSize);
        Page<CommentDO> result = commentMapper.selectPage(page, new QueryWrapper<>());
        return dataTransformUtil.commentDOToCommentVO(result);
    }

    @Override
    public Integer deleteCommentById(String id) {
        return commentMapper.deleteById(id);
    }

    @Override
    public List<CommentDO> searchComment(String keyWord, String select) {
        QueryWrapper<CommentDO> qw = new QueryWrapper<>();
        qw.like(select,keyWord);
        return commentMapper.selectList(qw);
    }

    @Override
    public Integer insertComment(List<CommentDO> comments) {
        for (CommentDO comment : comments) {
            // 遍历查出用户的头像
            String userId = comment.getUserId();
            List<Map<String, Object>> icon = userMapper.selectMaps(new QueryWrapper<UserDO>().select("icon").eq("id", userId));
            System.out.println("icon"+icon);
            comment.setIcon(icon.get(0).get("icon").toString());

            int insert = commentMapper.insert(comment);
            if (insert <= 0) {return 0;}
        }
        return 1;
    }
}
