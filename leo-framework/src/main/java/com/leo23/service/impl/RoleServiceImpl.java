package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.vo.PageVo;
import com.leo23.mapper.RoleMapper;
import com.leo23.domain.entity.Role;
import com.leo23.service.RoleService;
import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-01-13 08:21:20
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<String> selectRoleKeyByUserId(Long id) {
        //判断是否是管理员，是返回集合中只需要有admin
        if (SecurityUtils.isAdmin()) {
            List<String> roleKeys = new ArrayList<>();
            roleKeys.add("admin");
            return roleKeys;
        }
        //否则查询用户所具有的角色信息
        return baseMapper.selectRoleKeyByUserId(id);
    }

    @Override
    public ResponseResult<PageVo> getRoleList(Integer pageNum, Integer pageSize, Role role) {
        LambdaQueryWrapper<Role> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(role.getRoleName()), Role::getRoleName, role.getRoleName());
        wrapper.eq(StringUtils.hasText(role.getStatus()), Role::getStatus, role.getStatus());
        Page<Role> page = new Page<>(pageNum, pageSize);
        page(page, wrapper);
        PageVo pageVo = new PageVo(page.getRecords(), page.getTotal());
        return ResponseResult.okResult(pageVo);
    }
}

