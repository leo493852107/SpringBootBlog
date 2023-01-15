package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.dto.RoleDto;
import com.leo23.domain.dto.UpdateRoleMenuDto;
import com.leo23.domain.entity.RoleMenu;
import com.leo23.domain.vo.PageVo;
import com.leo23.domain.vo.RoleVo;
import com.leo23.mapper.RoleMapper;
import com.leo23.domain.entity.Role;
import com.leo23.service.RoleMenuService;
import com.leo23.service.RoleService;
import com.leo23.utils.BeanCopyUtils;
import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色信息表(Role)表服务实现类
 *
 * @author makejava
 * @since 2023-01-13 08:21:20
 */
@Service("roleService")
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Resource
    private RoleMenuService roleMenuService;

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
        List<RoleVo> roleVos = BeanCopyUtils.copyBeanList(page.getRecords(), RoleVo.class);
        PageVo pageVo = new PageVo(roleVos, page.getTotal());
        return ResponseResult.okResult(pageVo);
    }

    @Override
    public ResponseResult updateRoleStatus(RoleDto roleDto) {
        Role role = new Role();
        role.setId(roleDto.getRoleId());
        role.setStatus(roleDto.getStatus());
        baseMapper.updateById(role);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult getRoleById(Long id) {
        RoleVo roleVo = BeanCopyUtils.copyBean(getById(id), RoleVo.class);
        return ResponseResult.okResult(roleVo);
    }

    @Override
    public ResponseResult updateRoleAndRoleMenu(UpdateRoleMenuDto updateRoleMenuDto) {
        // 更新角色role 信息
        Role role = BeanCopyUtils.copyBean(updateRoleMenuDto, Role.class);
        updateById(role);
        // 更新role_menu信息，先删除包含role_id数据，再增加
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, updateRoleMenuDto.getId());
        roleMenuService.remove(wrapper);
        for (String menuId : updateRoleMenuDto.getMenuIds()) {
            roleMenuService.save(new RoleMenu(updateRoleMenuDto.getId(), Long.parseLong(menuId)));
        }
        return ResponseResult.okResult();
    }
}

