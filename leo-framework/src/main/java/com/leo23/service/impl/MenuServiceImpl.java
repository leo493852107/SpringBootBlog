package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.constants.SystemConstants;
import com.leo23.domain.ResponseResult;
import com.leo23.mapper.MenuMapper;
import com.leo23.domain.entity.Menu;
import com.leo23.service.MenuService;
import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单权限表(Menu)表服务实现类
 *
 * @author makejava
 * @since 2023-01-13 08:17:06
 */
@Service("menuService")
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public List<String> selectPermsByUserId(Long id) {
        // 如果是管理员，返回所有权限(Permission中需要的所有菜单类型为C或者F的，状态为正常的，未被删除的权限)
        if (SecurityUtils.isAdmin()) {
            LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
            wrapper.in(Menu::getMenuType, SystemConstants.MENU_TYPE_MENU, SystemConstants.MENU_TYPE_BUTTON);
            wrapper.eq(Menu::getStatus, SystemConstants.STATUS_NORMAL);
            List<Menu> menus = list(wrapper);
            List<String> perms = menus.stream().map(Menu::getPerms).collect(Collectors.toList());
            return perms;
        }
        // 筛选用户所具有的权限
        return baseMapper.selectPermsByUserId(id);
    }

    @Override
    public List<Menu> selectRouterMenuTreeByUserId(Long userId) {
        List<Menu> menus = null;
        // 判断是否是管理员，是 返回所有符合要求的menu
        if (SecurityUtils.isAdmin()) {
            menus = baseMapper.selectAllRouterMenu();
        } else {
            // 当前用户具有的Menu
            menus = baseMapper.selectRouterMenuTreeByUserId(userId);
        }
        // 构建tree
        List<Menu> menuTree = buildMenuTree(menus, 0L);
        return menuTree;
    }

    private List<Menu> buildMenuTree(List<Menu> menus, Long parentId) {
        List<Menu> menuTree = menus.stream().filter(menu -> menu.getParentId().equals(parentId))
                .map(menu -> menu.setChildren(getChildren(menu, menus)))
                .collect(Collectors.toList());
        return menuTree;
    }

    /**
     * 获取传入参数的 子Menu集合
     *
     * @param menu
     * @param menus
     * @return
     */
    private List<Menu> getChildren(Menu menu, List<Menu> menus) {
        List<Menu> childrenList = menus.stream()
                .filter(m -> m.getParentId().equals(menu.getId()))
                .map(m -> m.setChildren(getChildren(m, menus))) // 递归调用
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult getMenus(Menu menu) {
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.like(StringUtils.hasText(menu.getMenuName()), Menu::getMenuName, menu.getMenuName());
        wrapper.eq(StringUtils.hasText(menu.getStatus()), Menu::getStatus, menu.getStatus());
        wrapper.orderByAsc(Menu::getParentId).orderByAsc(Menu::getOrderNum);
        return ResponseResult.okResult(list(wrapper));
    }
}

