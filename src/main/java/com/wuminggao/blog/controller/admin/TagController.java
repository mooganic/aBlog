package com.wuminggao.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.service.TagService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;

/**
 * @author wuminggao
 * @create 2020-08-01-上午12:36
 */
@Controller
@RequestMapping("/admin")
public class TagController {

    @Autowired
    TagService tagService;

    /**
     *
     * @param current 要查询的页码 默认为1
     * @param size 每页的条数 默认为10
     * @return
     */
    @GetMapping("/tags")
    public ModelAndView listTags(@RequestParam(defaultValue = "1") Integer current,
                                  @RequestParam(defaultValue = "10") Integer size){
        Page<Tag> page = new Page<>(current, size);
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id"); // 根据id 降序排列
        IPage<Tag> tagPage = tagService.page(page, wrapper);
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", tagPage);
        modelAndView.setViewName("admin/tags");
        return modelAndView;
    }

    @GetMapping("/tags/input")
    public String input(Model model){
        model.addAttribute("tag", new Tag());
        return "admin/tag-input";
    }

    /**
     * 新增一个标签
     * @param tag
     * @param attributes
     * @return
     */
    @PostMapping("/tags")
    public String addNewTag(Tag tag, RedirectAttributes attributes,
                             HttpServletRequest request){
        QueryWrapper<Tag> wrapper = new QueryWrapper<>();
        wrapper.eq("name", tag.getName());
        Tag one = tagService.getOne(wrapper);

        if (one != null){ // 已经存在一个同名标签
            request.setAttribute("message", "操作失败，存在同名标签");
            return "admin/tag-input";
        }else {
            boolean save = tagService.save(tag);
            if (save == false){
                //保存失败
                attributes.addFlashAttribute("message", "保存失败，出现错误");
            }else {
                // 保存成功
                attributes.addFlashAttribute("message", "操作成功");
            }
            return "redirect:/admin/tags";
        }
    }

    @GetMapping("/tags/{id}/input")
    public String toEditTag(@PathVariable("id") Long id, Model model){
        Tag tag = tagService.getById(id);
        model.addAttribute("tag", tag);
        return "admin/tag-input"; //去到编辑页面，和新增页面共用
    }

    /**
     * 修改标签名字
     * @param id
     * @param tag
     * @param attributes
     * @param request
     * @return
     */
    @PostMapping("/tags/{id}")
    public String editTagValid(@PathVariable Long id,
                                Tag tag,
                                RedirectAttributes attributes,
                                HttpServletRequest request) {
        Tag one = tagService.getOne(new QueryWrapper<Tag>().eq("name", tag.getName()));

        if (one != null){ // 已经存在一个同名标签
            request.setAttribute("message", "操作失败，存在同名标签");
            return "admin/tag-input";

        }else {
            boolean update = tagService.updateById(tag);
            if (!update){
                //保存失败
                attributes.addFlashAttribute("message", "保存失败，出现错误");
            }else {
                // 保存成功
                attributes.addFlashAttribute("message", "操作成功");
            }
            return "redirect:/admin/tags";
        }
    }

    @GetMapping("/tags/{id}/delete")
    public String deleteTag(@PathVariable("id") Long id, RedirectAttributes attributes){
        Tag tag = tagService.getById(id);
        boolean remove = tagService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message", "删除标签("+ tag.getName()+ ")成功");
        }else {
            attributes.addFlashAttribute("message", "删除标签("+ tag.getName()+ ")失败");
        }
        return "redirect:/admin/tags";
    }

}
