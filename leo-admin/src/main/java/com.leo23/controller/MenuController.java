package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.Menu;
import com.leo23.service.MenuService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;

@RestController
@RequestMapping("/system/menu")
public class MenuController {
    @Resource
    private MenuService menuService;

    /**
     * 展示菜单列表，不需要分页，可以针对菜单名称模糊查询，也可以根据菜单状态查询，按照父菜单id和orderNum排序
     *
     * @param menu
     * @return
     */
    @GetMapping("/list")
    public ResponseResult getMenus(Menu menu) {
        return menuService.getMenus(menu);
    }

    @PostMapping
    public ResponseResult add(@RequestBody Menu menu) {
        menuService.save(menu);
        return ResponseResult.okResult();
    }

    @GetMapping("/{id}")
    public ResponseResult getMenuById(@PathVariable("id") Long id) {
        return menuService.getMenuById(id);
    }

    @PutMapping
    public ResponseResult updateMenu(@RequestBody Menu menu) {
        return ResponseResult.okResult(menuService.updateById(menu));
    }

    @DeleteMapping("{id}")
    public ResponseResult deleteMenuById(@PathVariable("id") Long id) {
        return menuService.deleteMenuById(id);
    }

    @GetMapping("/treeselect")
    public ResponseResult treeSelect() {
        return menuService.treeSelect();
    }
}
