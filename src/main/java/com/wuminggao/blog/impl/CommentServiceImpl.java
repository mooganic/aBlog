package com.wuminggao.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuminggao.blog.entity.Comment;
import com.wuminggao.blog.mapper.CommentMapper;
import com.wuminggao.blog.service.CommentService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class CommentServiceImpl extends ServiceImpl<CommentMapper, Comment> implements CommentService {

    @Override
    public List<Comment> getCommentByBlogId(Long blogId) {
        //查询得到该博客所有的父级评论
        List<Comment> commentList = baseMapper.selectParentComment(blogId);
        resetReplyComment(commentList, blogId);
        return commentList;
    }

    private List<Comment> tempReplyList = new ArrayList<>();

    /**
     * 根据父级评论的所有一级子评论，重新将父级评论下的所有一级评论、二级评论...都设置为父级评论的一级子评论
     *
     * @param commentList
     * @return
     */
    private void resetReplyComment(List<Comment> commentList, Long blogId) {
        for (Comment comment : commentList) {
            //获取所有一级子评论的集合
            List<Comment> replyComments = comment.getReplyComments();
            //循环遍历所有的一级子评论，将二级子评论及以下都添加到临时集合
            for (Comment replyComment : replyComments) {
                recursively(replyComment, blogId);
            }
            //修改父评论的子评论为迭代修改后的评论集合
            comment.setReplyComments(tempReplyList);
            //重置临时存放的评论
            tempReplyList = new ArrayList<>();
        }
    }

    /**
     * 递归遍历所有子评论，都添加到临时区
     *
     * @param replyComment
     */
    private void recursively(Comment replyComment, Long blogId) {
        tempReplyList.add(replyComment);
        //获取该一级子评论的所有二级子评论集合
        Comment comment = baseMapper.selectChildCommentsByBlogIdAndCommentId(blogId, replyComment.getId());
        if (comment != null){ //为空 则说明没有二级子评论
            List<Comment> replyComments = comment.getReplyComments();
            if (replyComments.size() != 0) {
                for (Comment comment1: replyComments){
                    recursively(comment1, blogId);
                }
            }
        }
    }
}
