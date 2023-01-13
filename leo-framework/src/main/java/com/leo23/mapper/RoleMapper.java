package com.leo23.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo23.domain.entity.Role;

import java.util.List;

/**
 * 角色信息表(Role)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-13 08:21:20
 */
public interface RoleMapper extends BaseMapper<Role> {

    List<String> selectRoleKeyByUserId(Long userId);
}

