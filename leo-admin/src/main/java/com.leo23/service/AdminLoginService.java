package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.User;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-01-12 22:21:07
 */
public interface AdminLoginService extends IService<User> {
    ResponseResult login(User user);
}

