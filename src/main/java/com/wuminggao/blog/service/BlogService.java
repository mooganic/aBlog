package com.wuminggao.blog.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.github.pagehelper.PageInfo;
import com.wuminggao.blog.entity.Blog;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
public interface BlogService extends IService<Blog> {

    /**
     * Blog getBlog(Long id) -> T getById(Serializable id);
     *
     * Page<Blog> blogList -> IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
     *
     * Blog saveBlog(Blog blog) -> boolean save(T entity);
     *
     * Blog updateBlog(Long id, Blog blog) -> boolean updateById(T entity);
     *
     * void deleteBlog(Long id) ->  boolean removeById(Serializable id);
     */

    List<Blog> searchBlogListByCondition(String title, Long typeId, boolean recommend);

    List<Blog> searchBlogListByCondition(String content, boolean all);

    List<Blog> listIndexBlog();

    List<Blog> listBlogByType(Long id);

    List<Blog> listBlogByTag(Long id);

    Blog getDetailBlogById(Long id) throws Exception;

    Map<String, List<Blog>> archiveBlog();
}
