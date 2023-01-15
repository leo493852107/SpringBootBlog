package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.RoleDto;
import com.leo23.domain.dto.RoleMenuDto;
import com.leo23.domain.entity.Role;
import com.leo23.domain.entity.RoleMenu;
import com.leo23.domain.vo.PageVo;
import com.leo23.service.RoleMenuService;
import com.leo23.service.RoleService;
import com.leo23.utils.BeanCopyUtils;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/role")
public class RoleController {
    @Resource
    private RoleService roleService;
    @Resource
    private RoleMenuService roleMenuService;

    @GetMapping("/list")
    public ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, Role role) {
        return roleService.getRoleList(pageNum, pageSize, role);
    }

    @PutMapping("/changeStatus")
    public ResponseResult updateRoleStatus(@RequestBody RoleDto roleDto) {
        return roleService.updateRoleStatus(roleDto);
    }

    @PostMapping
    public ResponseResult add(@RequestBody RoleMenuDto roleMenuDto) {
        Role role = BeanCopyUtils.copyBean(roleMenuDto, Role.class);
        roleService.save(role);
        // 1对都关联 role & menu
        roleMenuService.saveRoleMenu(role.getId(), roleMenuDto.getMenuIds());
        return ResponseResult.okResult();
    }
}
