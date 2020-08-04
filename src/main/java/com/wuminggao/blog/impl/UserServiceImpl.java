package com.wuminggao.blog.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.wuminggao.blog.entity.User;
import com.wuminggao.blog.mapper.UserMapper;
import com.wuminggao.blog.service.UserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.wuminggao.blog.util.MD5Utils;
import org.springframework.beans.factory.annotation.Autowired;
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
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Autowired
    UserMapper userMapper;

    @Override
    public User checkUser(String username, String password) {
        QueryWrapper<User> wrapper = new QueryWrapper<>();
        wrapper.eq("username", username).eq("password", MD5Utils.code(password));
        User user = userMapper.selectOne(wrapper);
        return user;
    }
}
