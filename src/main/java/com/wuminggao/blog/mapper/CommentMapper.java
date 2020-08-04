package com.wuminggao.blog.mapper;

import com.wuminggao.blog.entity.Comment;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Repository
public interface CommentMapper extends BaseMapper<Comment> {

    List<Comment> selectCommentByBlogId();

    /**
     * 根据博客id，查询所有的父级评论和他们的子评论
     */
    List<Comment> selectParentComment(Long blogId);

    /**
     * 根据博客id和子评论的id，查询二级子评论
     */
    Comment selectChildCommentsByBlogIdAndCommentId(Long blogId, Long commentId);
}
