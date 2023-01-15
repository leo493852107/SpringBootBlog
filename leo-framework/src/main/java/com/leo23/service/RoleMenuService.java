package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.entity.RoleMenu;

import java.util.List;

/**
 * 角色和菜单关联表(RoleMenu)表服务接口
 *
 * @author makejava
 * @since 2023-01-15 12:59:39
 */
public interface RoleMenuService extends IService<RoleMenu> {
    // 先去数据库删除role_id相同字段的数据，再新增对应的menu_id
    void saveRoleMenu(Long id, List<String> menuIds);
}

