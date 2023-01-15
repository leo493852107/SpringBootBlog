package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.RoleDto;
import com.leo23.domain.dto.UpdateRoleMenuDto;
import com.leo23.domain.entity.Role;
import com.leo23.domain.vo.PageVo;

import java.util.List;

/**
 * 角色信息表(Role)表服务接口
 *
 * @author makejava
 * @since 2023-01-13 08:21:20
 */
public interface RoleService extends IService<Role> {

    List<String> selectRoleKeyByUserId(Long id);

    ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, Role role);

    ResponseResult updateRoleStatus(RoleDto roleDto);

    // 角色信息回显
    ResponseResult getRoleById(Long id);

    // 更新角色信息
    ResponseResult updateRoleAndRoleMenu(UpdateRoleMenuDto updateRoleMenuDto);

    ResponseResult deleteRoleById(Long id);

    // 获取状态正常的所有角色
    ResponseResult listAllRole();
}

