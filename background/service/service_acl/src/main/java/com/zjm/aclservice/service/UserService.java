package com.zjm.aclservice.service;

import com.zjm.aclservice.entity.User;
import com.baomidou.mybatisplus.extension.service.IService;

/**
 * 用户表 服务类
 * @author ZengJinming
 * @since 2020-04-16
 */
public interface UserService extends IService<User> {

    // 从数据库中取出用户信息
    User selectByUsername(String username);
}
