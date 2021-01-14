package com.zjm.aclservice.service.impl;

import com.zjm.aclservice.entity.User;
import com.zjm.aclservice.mapper.UserMapper;
import com.zjm.aclservice.service.UserService;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

/**
 * 用户表 服务实现类
 * @author ZengJinming
 * @since 2020-04-16
 */
@Service
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public User selectByUsername(String username) {
        return baseMapper.selectOne(new QueryWrapper<User>().eq("username", username));
    }
}
