package com.wuminggao.blog.mapper;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.wuminggao.blog.entity.Blog;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.wuminggao.blog.entity.Tag;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Repository
public interface BlogMapper extends BaseMapper<Blog> {

    List<Blog> selectBlogListByCondition(String title, Long typeId, boolean recommend);

    List<Blog> selectIndexBlog();

    List<Blog> selectBlogByType(Long id);

    List<Blog> selectBlogByTag(Long id);

    List<Blog> selectBlogListByTitleAndContent(String content, boolean all);

    Blog selectDetailBlogById(Long id);

    Integer updateViews(Long id);

    // 根据博客id查询它的所有tag
    List<Tag> selectAllTagByBlogId(Long id);

    List<String> findGroupYear();

    List<Blog> findBlogByYear(String year);
}
