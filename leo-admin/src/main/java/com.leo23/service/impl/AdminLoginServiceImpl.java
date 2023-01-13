package com.leo23.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.LoginUser;
import com.leo23.domain.entity.User;
import com.leo23.mapper.UserMapper;
import com.leo23.service.AdminLoginService;
import com.leo23.utils.JwtUtil;
import com.leo23.utils.RedisCache;
import com.leo23.utils.SecurityUtils;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

/**
 * 用户表(User)表服务实现类
 *
 * @author makejava
 * @since 2023-01-12 22:21:09
 */
@Service("adminUserService")
public class AdminLoginServiceImpl extends ServiceImpl<UserMapper, User> implements AdminLoginService {
    @Resource
    private AuthenticationManager authenticationManager;
    @Resource
    private RedisCache redisCache;

    @Override
    public ResponseResult login(User user) {
        UsernamePasswordAuthenticationToken token = new UsernamePasswordAuthenticationToken(user.getUserName(), user.getPassword());
        Authentication authenticate = authenticationManager.authenticate(token);
        // 判断是否认证通过
        if (Objects.isNull(authenticate)) {
            throw new RuntimeException("用户名/密码错误");
        }
        // 获取userid 生成 token
        LoginUser loginUser = (LoginUser) authenticate.getPrincipal();
        String userId = loginUser.getUser().getId().toString();
        String jwt = JwtUtil.createJWT(userId);
        // 用户信息存入redis
        redisCache.setCacheObject("login:" + userId, loginUser);

        // 封装token，返回
        Map<String, String> map = new HashMap<>();
        map.put("token", jwt);
        return ResponseResult.okResult(map);
    }

    @Override
    public ResponseResult logout() {
        // 获取用户id，redis删除对应的值
        Long userId = SecurityUtils.getUserId();
        redisCache.deleteObject("login:" + userId);
        return ResponseResult.okResult();
    }
}

