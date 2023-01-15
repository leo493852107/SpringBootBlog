package com.leo23.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.leo23.constants.SystemConstants;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.RoleMenu;
import com.leo23.domain.vo.MenuVo;
import com.leo23.domain.vo.RoleMenuVo;
import com.leo23.enums.AppHttpCodeEnum;
import com.leo23.mapper.MenuMapper;
import com.leo23.domain.entity.Menu;
import com.leo23.service.MenuService;
import com.leo23.service.RoleMenuService;
import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
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
    @Resource
    private RoleMenuService roleMenuService;

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

    @Override
    public ResponseResult getMenuById(Long id) {
        return ResponseResult.okResult(baseMapper.selectById(id));
    }

    @Override
    public ResponseResult deleteMenuById(Long id) {
        // 查询是否具有子菜单，有子菜单告诉用户先删除子菜单
        LambdaQueryWrapper<Menu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(Menu::getParentId, id);
        if (baseMapper.selectCount(wrapper) > 0) {
            return ResponseResult.errorResult(AppHttpCodeEnum.HAS_CHILD_MENU_ERROR);
        }
        baseMapper.deleteById(id);
        return ResponseResult.okResult();
    }

    @Override
    public ResponseResult treeSelect() {
        List<MenuVo> menuVos = baseMapper.selectTreeSelectMenuVo();
        // 构建tree
        List<MenuVo> menuTree = buildMenuVoTree(menuVos);
        return ResponseResult.okResult(menuTree);
    }

    private List<MenuVo> buildMenuVoTree(List<MenuVo> menuVos) {
        List<MenuVo> menuVos1 = menuVos.stream()
                .filter(menuVo -> menuVo.getParentId().equals(0L)) // 获得parentId为0的顶级菜单
                .map(menuVo -> menuVo.setChildren(getMenuVoChildren(menuVo, menuVos)))
                .collect(Collectors.toList());
        return menuVos1;
    }

    private List<MenuVo> getMenuVoChildren(MenuVo menuVo, List<MenuVo> menuVos) {
        List<MenuVo> childrenList = menuVos.stream()
                .filter(m -> m.getParentId().equals(menuVo.getId()))
                .map(m -> m.setChildren(getMenuVoChildren(m, menuVos)))
                .collect(Collectors.toList());
        return childrenList;
    }

    @Override
    public ResponseResult roleMenuTreeselect(Long id) {
        // menus 菜单树
        List<MenuVo> menuVos = baseMapper.selectTreeSelectMenuVo();
        // 构建tree
        List<MenuVo> menuTree = buildMenuVoTree(menuVos);
        // checkedKeys 角色所关联的菜单权限id列表
        LambdaQueryWrapper<RoleMenu> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(RoleMenu::getRoleId, id);
        List<RoleMenu> roleMenus = roleMenuService.list(wrapper);
        List<Long> menuIds = roleMenus.stream().map(RoleMenu::getMenuId).collect(Collectors.toList());
        return ResponseResult.okResult(new RoleMenuVo(menuTree, menuIds));
    }
}

