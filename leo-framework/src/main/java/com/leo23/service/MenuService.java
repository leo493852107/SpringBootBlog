package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.Menu;

import java.util.List;

/**
 * 菜单权限表(Menu)表服务接口
 *
 * @author makejava
 * @since 2023-01-13 08:17:06
 */
public interface MenuService extends IService<Menu> {

    List<String> selectPermsByUserId(Long id);

    List<Menu> selectRouterMenuTreeByUserId(Long userId);

    // 展示菜单列表，不需要分页，可以针对菜单名称模糊查询，也可以根据菜单状态查询，按照父菜单id和orderNum排序
    ResponseResult getMenus(Menu menu);

    ResponseResult getMenuById(Long id);

    // 有子菜单不允许删除
    ResponseResult deleteMenuById(Long id);
}

