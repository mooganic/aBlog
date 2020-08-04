package com.wuminggao.blog.mapper;

import com.wuminggao.blog.entity.Tag;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
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
public interface TagMapper extends BaseMapper<Tag> {


    List<Tag> selectTopList(int size);
}
