package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.UserDto;
import com.leo23.domain.entity.User;
import com.leo23.domain.vo.PageVo;

/**
 * 用户表(User)表服务接口
 *
 * @author makejava
 * @since 2023-01-10 15:57:15
 */
public interface UserService extends IService<User> {

    ResponseResult userInfo();

    ResponseResult updateUserInfo(User user);

    ResponseResult register(User user);

    ResponseResult<PageVo> getUserList(Integer pageNum, Integer pageSize, User user);

    // 新增用户
    ResponseResult addUser(UserDto userDto);
}

