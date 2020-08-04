package com.wuminggao.blog.service;

import com.wuminggao.blog.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author wuminggao
 * @since 2020-07-31
 */
public interface UserService extends IService<User> {

    public User checkUser(String username, String password);

}
