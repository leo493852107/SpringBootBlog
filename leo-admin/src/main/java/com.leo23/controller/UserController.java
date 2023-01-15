package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.User;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/user")
public class UserController {
    @Resource
    private UserService userService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getUserList(Integer pageNum, Integer pageSize, User user) {
        return userService.getUserList(pageNum, pageSize, user);
    }
}
