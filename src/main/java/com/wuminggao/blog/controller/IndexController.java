package com.wuminggao.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.entity.Comment;
import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.entity.Type;
import com.wuminggao.blog.service.BlogService;
import com.wuminggao.blog.service.CommentService;
import com.wuminggao.blog.service.TagService;
import com.wuminggao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuminggao
 * @create 2020-07-27-下午57
 */
@Controller
public class IndexController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

    @GetMapping(value = "/")
    public String index(@RequestParam(defaultValue = "1") Integer current,
                    @RequestParam(defaultValue = "10") Integer size,
                    Model model) {
        //按照分类查询博客数量的若干条记录,默认为6
        List<Type> typeList = typeService.getTopList(6);
        //按照标签查询博客数量的若干条记录,默认为10
        List<Tag> tagList = tagService.getTopList(10);
        //查询最新推荐的10篇文章
        List<Blog> recommendBlogList =
                blogService.page(new Page<Blog>(1, 10),
                        new QueryWrapper<Blog>()
                                .eq("recommend", true)
                                .eq("published", true)
                                .orderByDesc("update_time")).getRecords();
        //分页查询首页的blog展示
        PageHelper.startPage(current, size);
        List<Blog> blogList = blogService.listIndexBlog();
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("page", pageInfo);
        model.addAttribute("types", typeList);
        model.addAttribute("tags", tagList);
        model.addAttribute("recommendBlogs", recommendBlogList);
        return "index";
    }


    @GetMapping(value = "/blog/{id}")
    public String blog(@PathVariable Long id, Model model) throws Exception {
        Blog blog = blogService.getDetailBlogById(id);
        model.addAttribute("blog", blog);
        return "blog";
    }

    @GetMapping("/search")
    public String search(@RequestParam(defaultValue = "1") Integer current,
                         @RequestParam(defaultValue = "10") Integer size,
                         @RequestParam(defaultValue = "") String query,
                         Model model){
        PageHelper.startPage(current, size);
        List<Blog> blogList = blogService.searchBlogListByCondition(query, false);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("page", pageInfo);
        model.addAttribute("query", query);
        return "search";
    }

    @GetMapping("/footer/newblog")
    public String newBlogs(Model model){
        List<Blog> recommendBlogList =
                blogService.page(new Page<Blog>(1, 3),
                        new QueryWrapper<Blog>()
                                .eq("recommend", true)
                                .eq("published", true)
                                .orderByDesc("update_time")).getRecords();
        model.addAttribute("newBlogs",recommendBlogList);
        return "_fragments :: newBlogList";
    }


}
