package com.wuminggao.blog.service;

import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.wuminggao.blog.entity.Type;
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
public interface TypeService extends IService<Type> {

    List<Type> getTopList(Integer size);

    /**
     * Type saveType(Type type) -> boolean save(T entity);
     *
     * Type getType(Long id) ->   T getById(Serializable id);
     *
     * Page<Type> listType(Pageable pageable) -> IPage<T> page(IPage<T> page, Wrapper<T> queryWrapper);
     *
     *Type updateType(Long id, Type type) -> boolean updateById(T entity);
     *
     * void deleteType(Long id) ->  boolean removeById(Serializable id);
     */


}
