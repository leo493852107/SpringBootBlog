package com.leo23.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.leo23.domain.entity.Menu;
import com.leo23.domain.vo.MenuVo;

import java.util.List;

/**
 * 菜单权限表(Menu)表数据库访问层
 *
 * @author makejava
 * @since 2023-01-13 08:17:06
 */
public interface MenuMapper extends BaseMapper<Menu> {

    // 获取用户权限C,F;  菜单类型（M目录 C菜单 F按钮）
    List<String> selectPermsByUserId(Long userId);

    List<Menu> selectAllRouterMenu();

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    List<MenuVo> selectTreeSelectMenuVo();
}

