package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.User;
import com.leo23.service.AdminLoginService;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
public class AdminLoginController {
    @Resource
    private AdminLoginService adminLoginService;

    @PostMapping("/user/login")
    public ResponseResult adminLogin(@RequestBody User user) {
        return adminLoginService.login(user);
    }
}
