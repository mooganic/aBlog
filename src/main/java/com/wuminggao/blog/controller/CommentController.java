package com.wuminggao.blog.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuminggao.blog.entity.Comment;
import com.wuminggao.blog.entity.User;
import com.wuminggao.blog.service.CommentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import org.springframework.stereotype.Controller;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * <p>
 *  前端控制器
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Controller
public class CommentController {

    @Autowired
    CommentService commentService;

    @Value("${comment.avatar}")
    private String avatar;

    @GetMapping("/comments/{blogId}")
    public String comment(@PathVariable Long blogId, Model model){
        List<Comment> commentList =
                commentService.getCommentByBlogId(blogId);
         model.addAttribute("comments", commentList);
        return "blog :: commentList";
    }

    @PostMapping("/comments")
    public String post(Comment comment, HttpSession session){
        User user = (User)session.getAttribute("user");
        if (user != null){
            comment.setAvatar(user.getAvatar());
            comment.setAdminComment(true);
            comment.setNickname(user.getNickname());
        }else {
            comment.setAvatar(avatar);
        }
        commentService.save(comment);
        return "redirect:/comments/" + comment.getBlogId();
    }

}

