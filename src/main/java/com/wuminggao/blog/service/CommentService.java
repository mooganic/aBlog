package com.wuminggao.blog.service;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuminggao.blog.entity.Comment;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
public interface CommentService extends IService<Comment> {

    List<Comment> getCommentByBlogId(Long blogId);
}
