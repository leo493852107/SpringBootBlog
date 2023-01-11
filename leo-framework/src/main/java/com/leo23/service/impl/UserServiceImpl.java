package com.leo23.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.vo.UserInfoVo;
import com.leo23.mapper.UserMapper;
import com.leo23.domain.entity.User;
import com.leo23.service.UserService;
import com.leo23.utils.BeanCopyUtils;
import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-01-10 15:57:15
 */
@Service("userService")
public class UserServiceImpl extends ServiceImpl<UserMapper, User> implements UserService {

    @Override
    public ResponseResult userInfo() {
        // 获取用户id
        Long userId = SecurityUtils.getUserId();
        // 根据id查用户信息
        User user = getById(userId);
        // 封装UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(user, UserInfoVo.class);
        return ResponseResult.okResult(userInfoVo);
    }

    @Override
    public ResponseResult updateUserInfo(User user) {
        updateById(user);
        return ResponseResult.okResult();
    }
}

