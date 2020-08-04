package com.wuminggao.blog.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.service.BlogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * @author wuminggao
 * @create 2020-08-04-下午4:01
 */
@Controller
public class ArchivesShowController {

    @Autowired
    BlogService blogService;

    @GetMapping("/archives")
    public String archives(Model model){
        //返回所有文章构成的map
        model.addAttribute("archiveMap", blogService.archiveBlog());

        //返回所有文章的条目数
        model.addAttribute("blogCount", blogService.count(new QueryWrapper<Blog>().eq("published", true)));
        return "archives";
    }
}
