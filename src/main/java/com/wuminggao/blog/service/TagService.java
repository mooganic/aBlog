package com.wuminggao.blog.service;

import com.wuminggao.blog.entity.Tag;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
public interface TagService extends IService<Tag> {

    List<Tag> getTagListByString(String tagIds);

    List<Tag> getTopList(int size);
}
