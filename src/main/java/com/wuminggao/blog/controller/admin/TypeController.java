package com.wuminggao.blog.controller.admin;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuminggao.blog.entity.Type;
import com.wuminggao.blog.service.TypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.data.web.SpringDataWebProperties;
import org.springframework.http.HttpRequest;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * @author wuminggao
 * @create 2020-08-01-上午12:36
 */
@Controller
@RequestMapping("/admin")
public class TypeController {

    @Autowired
    TypeService typeService;

    /**
     *
     * @param current 要查询的页码 默认为1
     * @param size 每页的条数 默认为10
     * @return
     */
    @GetMapping("/types")
    public ModelAndView listTypes(@RequestParam(defaultValue = "1") Integer current,
                                  @RequestParam(defaultValue = "10") Integer size){
        Page<Type> page = new Page<>(current, size);
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id"); // 根据id 降序排列
        IPage<Type> typePage = typeService.page(page, wrapper);
//        System.out.println(typePage.getPages());
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("page", typePage);
        modelAndView.setViewName("admin/types");
        return modelAndView;
    }

    @GetMapping("/types/input")
    public String input(Model model){
        model.addAttribute("type", new Type());
        return "admin/type-input";
    }

    /**
     * 新增一个分类
     * @param type
     * @param attributes
     * @return
     */
    @PostMapping("/types")
    public String addNewType(Type type, RedirectAttributes attributes,
                             HttpServletRequest request){
        QueryWrapper<Type> wrapper = new QueryWrapper<>();
        wrapper.eq("name", type.getName());
        Type one = typeService.getOne(wrapper);

        if (one != null){ // 已经存在一个同名的
            request.setAttribute("message", "操作失败，存在同名分类");
            return "admin/type-input";
        }else {
            boolean save = typeService.save(type);
            if (!save){
                //保存失败
                attributes.addFlashAttribute("message", "保存失败，出现错误");
            }else {
                // 保存成功
                attributes.addFlashAttribute("message", "操作成功");
            }
            return "redirect:/admin/types";
        }
    }

    @GetMapping("/types/{id}/input")
    public String toEditType(@PathVariable("id") Long id, Model model){
        Type type = typeService.getById(id);
        model.addAttribute("type", type);
        return "admin/type-input"; //去到编辑页面，和新增页面共用
    }

    /**
     * 修改分类名字
     * @param id
     * @param type
     * @param attributes
     * @param request
     * @return
     */
    @PostMapping("/types/{id}")
    public String editTypeValid(@PathVariable Long id,
                                Type type,
                                RedirectAttributes attributes,
                                HttpServletRequest request) {
//        QueryWrapper<Type> wrapper = new QueryWrapper<Type>().eq("name", type.getName());
//        wrapper.eq("name", type.getName());
        Type one = typeService.getOne(new QueryWrapper<Type>().eq("name", type.getName()));

        if (one != null){ // 已经存在一个同名分类
            request.setAttribute("message", "操作失败，存在同名分类");
//            attributes.addFlashAttribute("message", "操作失败，存在同名分类");
//            attributes.addFlashAttribute("type", type);
            return "admin/type-input";

        }else {
            boolean update = typeService.updateById(type);
            if (!update){
                //保存失败
                attributes.addFlashAttribute("message", "保存失败，出现错误");
            }else {
                // 保存成功
                attributes.addFlashAttribute("message", "操作成功");
            }
            return "redirect:/admin/types";
        }
    }

    @GetMapping("/types/{id}/delete")
    public String deleteType(@PathVariable("id") Long id, RedirectAttributes attributes){
        Type type = typeService.getById(id);
        boolean remove = typeService.removeById(id);
        if (remove){
            attributes.addFlashAttribute("message", "删除分类("+ type.getName()+ ")成功");
        }else {
            attributes.addFlashAttribute("message", "删除分类("+ type.getName()+ ")失败");
        }
        return "redirect:/admin/types";
    }

}
