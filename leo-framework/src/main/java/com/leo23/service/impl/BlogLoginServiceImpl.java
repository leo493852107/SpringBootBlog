package com.leo23.service.impl;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.LoginUser;
import com.leo23.domain.entity.User;
import com.leo23.domain.vo.BlogUserLoginVo;
import com.leo23.domain.vo.UserInfoVo;
import com.leo23.service.BlogLoginService;
import com.leo23.utils.BeanCopyUtils;
import com.leo23.utils.JwtUtil;
import com.leo23.utils.RedisCache;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Objects;

@Service
public class BlogLoginServiceImpl implements BlogLoginService {
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
        redisCache.setCacheObject("bloglogin:" + userId, loginUser);

        // 把User转化为UserInfoVo
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        // 把token和userinfo封装 返回
        BlogUserLoginVo vo = new BlogUserLoginVo(jwt, userInfoVo);

        return ResponseResult.okResult(vo);
    }

    @Override
    public ResponseResult logout() {
        // 获取token
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        LoginUser loginUser = (LoginUser) authentication.getPrincipal();
        // 获取userId
        Long userId = loginUser.getUser().getId();
        // 删除redis中的用户信息
        redisCache.deleteObject("bloglogin:" + userId);
        return ResponseResult.okResult();
    }
}
