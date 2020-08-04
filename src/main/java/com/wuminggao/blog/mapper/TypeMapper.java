package com.wuminggao.blog.mapper;

import com.wuminggao.blog.entity.Type;
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
public interface TypeMapper extends BaseMapper<Type> {

    List<Type> selectTopList(Integer size);
}
