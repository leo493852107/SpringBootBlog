package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.mapper.RoleMenuMapper;
import com.leo23.domain.entity.RoleMenu;
import com.leo23.service.RoleMenuService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色和菜单关联表(RoleMenu)表服务实现类
 *
 * @author makejava
 * @since 2023-01-15 12:59:40
 */
@Service("roleMenuService")
public class RoleMenuServiceImpl extends ServiceImpl<RoleMenuMapper, RoleMenu> implements RoleMenuService {
    @Override
    public void saveRoleMenu(Long id, List<String> menuIds) {
        // 先去数据库删除role_id相同字段的数据，再新增对应的menu_id
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        remove(wrapper);
        for (String menuId : menuIds) {
            save(new RoleMenu(id, Long.parseLong(menuId)));
        }
    }
}

