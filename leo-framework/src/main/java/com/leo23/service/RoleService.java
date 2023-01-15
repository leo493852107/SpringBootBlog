package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
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
}

