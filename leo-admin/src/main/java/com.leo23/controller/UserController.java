package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.UserDto;
import com.leo23.domain.entity.User;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.UserService;
import org.springframework.web.bind.annotation.*;

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

    @PostMapping
    public ResponseResult addUser(@RequestBody UserDto userDto) {
        return userService.addUser(userDto);
    }

    @DeleteMapping("/{id}")
    public ResponseResult deleteUserById(@PathVariable("id") Long id) {
        return userService.deleteUserById(id);
    }
}
