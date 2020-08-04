package com.wuminggao.blog.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.entity.Type;
import com.wuminggao.blog.service.BlogService;
import com.wuminggao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author wuminggao
 * @create 2020-08-02-下午10:30
 */
@Controller
@RequestMapping("/types")
public class TypeShowController {

    @Autowired
    TypeService typeService;

    @Autowired
    BlogService blogService;

    @GetMapping
    public String types(Model model) {
        int count = typeService.count(null);
        List<Type> types = typeService.getTopList(count);
        model.addAttribute("types", types);
        return "types";
    }

    @GetMapping("/{id}")
    public String getBlogFromTypeId(@RequestParam(defaultValue = "1") Integer current,
                                    @RequestParam(defaultValue = "10") Integer size,
                                    @PathVariable Long id,
                                    Model model) {
        int count = typeService.count(null);
        List<Type> types = typeService.getTopList(count);
        model.addAttribute("types", types);
        //回传类型id
        model.addAttribute("activeTypeId", id);
        //根据类型获取对应的博客
        PageHelper.startPage(current, size);
        List<Blog> blogList = blogService.listBlogByType(id);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);
        model.addAttribute("pageInfo", pageInfo);
        return "types";
    }

}
