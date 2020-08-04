package com.wuminggao.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.entity.Type;
import com.wuminggao.blog.entity.User;
import com.wuminggao.blog.service.BlogService;
import com.wuminggao.blog.service.TagService;
import com.wuminggao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpSession;
import java.util.List;

/**
 * @author wuminggao
 * @create 2020-08-01-上午12:36
 */
@Controller
@RequestMapping("/admin")
public class BlogController {

    @Autowired
    BlogService blogService;

    @Autowired
    TypeService typeService;

    @Autowired
    TagService tagService;

    /**
     * 显示博客列表
     * @param current
     * @param size
     * @param model
     * @return
     */
    @GetMapping("/blogs")
    public String listBlog(@RequestParam(defaultValue = "1") Integer current,
                           @RequestParam(defaultValue = "10") Integer size,
                           Model model) {

        PageHelper.startPage(current, size);
        List<Blog> blogList = blogService.searchBlogListByCondition("", true);
        PageInfo<Blog> pageInfo = new PageInfo<>(blogList);

        model.addAttribute("pageInfo", pageInfo);
        model.addAttribute("types", typeService.list(null));
        return "admin/blogs";
    }

    /**
     * 后台管理 查询博客/翻页博客
     * @param current
     * @param size
     * @param title 根据标题内容模糊查询
     * @param typeId 根据类型搜索
     * @param recommend 搜索是否为推荐
     * @param search 是否为搜索
     * @param model
     * @return
     */
    @PostMapping("/blogs/search")
    public String searchBlogs(@RequestParam(defaultValue = "1") Integer current,
                              @RequestParam(defaultValue = "10") Integer size,
                              @RequestParam(value = "title", required = false) String title,
                              @RequestParam(value = "typeId", required = false) Long typeId,
                              @RequestParam(value = "recommend", required = false) boolean recommend,
                              @RequestParam(value = "search") boolean search,
                              Model model) {

        PageHelper.startPage(current, size);
        List<Blog> blogs;
        if (search == true){
             blogs = blogService.searchBlogListByCondition(title, typeId, recommend);
        }else {
            blogs = blogService.searchBlogListByCondition("", true);
        }
        PageInfo<Blog> pageInfo = new PageInfo<>(blogs);
        model.addAttribute("pageInfo", pageInfo);
        return "admin/blogs :: blogList";
    }

    /**
     * 编辑博客
     * @param id
     * @param model
     * @return
     */
    @GetMapping("/blogs/{id}/input")
    public String editBlog(@PathVariable Long id, Model model){
        Blog blog = blogService.getById(id);
        model.addAttribute("blog", blog);
        model.addAttribute("types", typeService.list(null));
        model.addAttribute("tags", tagService.list(null));
        return "admin/blog-input";
    }

    /**
     * 新增博客
     * @return
     */
    @GetMapping("/blogs/input")
    public String addNewBlog(Model model){
        model.addAttribute("blog", new Blog());
        model.addAttribute("types", typeService.list(null));
        model.addAttribute("tags", tagService.list(null));
        return "admin/blog-input";
    }

    /**
     * 博客更新或者新增处理
     * @param blog
     * @param session
     * @param attributes
     * @return
     */
    @PostMapping("/blogs")
    public String post(Blog blog, HttpSession session, RedirectAttributes attributes){

        //从session域获取user对象
        User user = (User) session.getAttribute("user");
//        blog.setUser(user);
        //设置userId
        blog.setUserId(user.getId());
        //设置tag集合
        blog.setTags(tagService.getTagListByString(blog.getTagIds()));
        boolean saveOrUpdate = false;
        if (blog.getId() == null){
            //新增博客
            saveOrUpdate = blogService.save(blog);
        }else {
            //修改博客
            saveOrUpdate = blogService.updateById(blog);
        }
        if (!saveOrUpdate) {
            attributes.addFlashAttribute("message", "操作失败");
        } else {
            attributes.addFlashAttribute("message", "操作成功");
        }
        return "redirect:/admin/blogs";
    }

    @GetMapping("/blogs/{id}/delete")
    public String deleteBlog(@PathVariable("id") Long id, RedirectAttributes attributes){
        Blog blog = blogService.getById(id);
        boolean remove = blogService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message", "删除博客("+ blog.getTitle()+ ")成功");
        }else {
            attributes.addFlashAttribute("message", "删除博客("+ blog.getTitle()+ ")失败");
        }
        return "redirect:/admin/blogs";
    }

}
