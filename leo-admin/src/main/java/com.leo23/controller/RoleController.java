package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.Role;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.RoleService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    private RoleService roleService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, Role role) {
        return roleService.getRoleList(pageNum, pageSize, role);
    }
}
