package com.wuminggao.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.service.BlogService;
import com.wuminggao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

/**
 * @author wuminggao
 * @create 2020-08-02-下午10:30
 */
@Controller
@RequestMapping("/tags")
public class TagShowController {

    @Autowired
    TagService tagService;

    @Autowired
    BlogService blogService;

    @GetMapping
    public String tags(Model model) {
        int count = tagService.count(null);
        List<Tag> tags = tagService.getTopList(count);
        model.addAttribute("tags", tags);
        return "tags";
    }

    @GetMapping("/{id}")
    public String getBlogFromTagId(@RequestParam(defaultValue = "1") Integer current,
                                   @RequestParam(defaultValue = "10") Integer size,
                                   @PathVariable Long id,
                                   Model model) {
        int count = tagService.count(null);
        List<Tag> tags = tagService.getTopList(count);
        model.addAttribute("tags", tags);
        //回传tag id
        model.addAttribute("activeTagId", id);
        //根据类型获取对应的博客
        PageHelper.startPage(current, size);
        List<Blog> blogList = blogService.listBlogByTag(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo", pageInfo);
        return "tags";
    }
}
