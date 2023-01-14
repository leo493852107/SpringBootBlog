package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.Menu;
import com.leo23.service.MenuService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 展示菜单列表，不需要分页，可以针对菜单名称模糊查询，也可以根据菜单状态查询，按照父菜单id和orderNum排序
     * @param menu
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getMenus(Menu menu) {
        return menuService.getMenus(menu);
    }

}
