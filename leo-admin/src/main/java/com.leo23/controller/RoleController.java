package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.RoleDto;
import com.leo23.domain.entity.Role;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.RoleService;
import org.springframework.web.bind.annotation.*;

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

    @PutMapping("/changeStatus")
    public ResponseResult updateRoleStatus(@RequestBody RoleDto roleDto) {
        return roleService.updateRoleStatus(roleDto);
    }
}
