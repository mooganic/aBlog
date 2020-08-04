package com.wuminggao.blog.impl;

import com.wuminggao.blog.entity.BlogTags;
import com.wuminggao.blog.mapper.BlogTagsMapper;
import com.wuminggao.blog.service.BlogTagsService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
@Service
@Transactional(rollbackFor = Exception.class)
public class BlogTagsServiceImpl extends ServiceImpl<BlogTagsMapper, BlogTags> implements BlogTagsService {

}
