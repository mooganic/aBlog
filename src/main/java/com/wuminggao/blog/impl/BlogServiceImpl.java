package com.wuminggao.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.wuminggao.blog.entity.BlogTags;
import com.wuminggao.blog.entity.Tag;
import com.wuminggao.blog.mapper.BlogMapper;
import com.wuminggao.blog.mapper.BlogTagsMapper;
import com.wuminggao.blog.service.BlogService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuminggao.blog.util.MarkDownUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.io.Serializable;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogServiceImpl extends ServiceImpl<BlogMapper, Blog> implements BlogService {

    @Autowired
    BlogTagsMapper blogTagsMapper;

    @Override
    public List<Blog> searchBlogListByCondition(String title, Long typeId, boolean recommend) {
        return baseMapper.selectBlogListByCondition(title, typeId, recommend);
    }

    /**
     * 根据内容或者标题模糊查询
     * @param content
     * @return
     */
    @Override
    public List<Blog> searchBlogListByCondition(String content, boolean all) {
        return baseMapper.selectBlogListByTitleAndContent(content, all);
    }

    /**
     * 查询Blog展示到首页，按照时间顺序降序排序
     * @return
     */
    @Override
    public List<Blog> listIndexBlog() {
        return baseMapper.selectIndexBlog();
    }

    @Override
    public List<Blog> listBlogByType(Long id) {
        return baseMapper.selectBlogByType(id);
    }

    @Override
    public List<Blog> listBlogByTag(Long id) {
        List<Blog> blogList = baseMapper.selectBlogByTag(id);
        if (blogList != null)
            for (Blog b:blogList)
                b.setTags(baseMapper.selectAllTagByBlogId(b.getId()));
        return blogList;
    }

    @Override
    public Map<String, List<Blog>> archiveBlog() {
        List<String> years = baseMapper.findGroupYear();
        Map<String, List<Blog>> map = new LinkedHashMap<>();
        for (String year : years) {
            map.put(year, baseMapper.findBlogByYear(year));
        }
        return map;
    }

    @Override
    public Blog getDetailBlogById(Long id) throws Exception {
        Blog blog = baseMapper.selectDetailBlogById(id);
        if (null == blog){
            throw new Exception("该博客不存在");
        }
        //更新观看次数
        baseMapper.updateViews(id);
        blog.setContent(MarkDownUtils.markdownToHtmlExtensions(blog.getContent()));
        return blog;
    }

    @Override
    public boolean save(Blog entity) {
        //保存blog到t_blog表，同时保存tag<->blog的关系到t_blog_tags表
        boolean save = super.save(entity);
        if (save) {
            //保存成功，获取文章id，删除之前的文章id->tag的关系，保存现在文章id->tag的关系
            saveBlogAndTags(entity.getId(), entity.getTags());
            return true;
        } else {
            return false;
        }
    }

    @Override
    public boolean updateById(Blog entity) {
        boolean update = super.updateById(entity);
        if (update) {
            saveBlogAndTags(entity.getId(), entity.getTags());
            return true;
        } else {
            return false;
        }

    }

    /**
     * 删除之前的文章id->tag的关系，保存现在文章id->tag的关系
     *
     * @param blogId
     * @param tagList
     */
    public void saveBlogAndTags(Long blogId, List<Tag> tagList) {
        blogTagsMapper.delete(new QueryWrapper<BlogTags>().eq("blog_id", blogId));
        //插入现有的id->tag的关系
        if (tagList != null){
            for (Tag tag : tagList) {
                blogTagsMapper.insert(new BlogTags(null, tag.getId(), blogId));
            }
        }
    }

    /**
     * 删除博文的同时，在t_blog_tags表中删除对应的tag
     * @param id
     * @return
     */
    @Override
    public boolean removeById(Serializable id) {
        blogTagsMapper.delete(new QueryWrapper<BlogTags>().eq("blog_id", id));
        return super.removeById(id);
    }
}
