package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.mapper.UserRoleMapper;
import com.leo23.domain.entity.UserRole;
import com.leo23.service.UserRoleService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 用户和角色关联表(UserRole)表服务实现类
 *
 * @author makejava
 * @since 2023-01-15 20:45:26
 */
@Service("userRoleService")
public class UserRoleServiceImpl extends ServiceImpl<UserRoleMapper, UserRole> implements UserRoleService {

    @Override
    public void saveUserRole(Long id, List<String> roleIds) {
        LambdaQueryWrapper<UserRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(UserRole::getUserId, id);
        remove(wrapper);
        for (String roleId : roleIds) {
            save(new UserRole(id, Long.parseLong(roleId)));
        }
    }
}

