package com.leo23.controller;

import com.leo23.domain.ResponseResult;
import com.leo23.domain.entity.LoginUser;
import com.leo23.domain.entity.User;
import com.leo23.domain.vo.AdminUserInfoVo;
import com.leo23.domain.vo.UserInfoVo;
import com.leo23.service.AdminLoginService;
import com.leo23.service.MenuService;
import com.leo23.service.RoleService;
import com.leo23.utils.BeanCopyUtils;
import com.leo23.utils.SecurityUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
public class AdminLoginController {
    @Resource
    private AdminLoginService adminLoginService;
    @Resource
    private MenuService menuService;
    @Resource
    private RoleService roleService;

    @PostMapping("/user/login")
    public ResponseResult adminLogin(@RequestBody User user) {
        return adminLoginService.login(user);
    }

    @GetMapping("/getInfo")
    public ResponseResult<AdminUserInfoVo> getInfo() {
        //获取当前登录的用户
        LoginUser loginUser = SecurityUtils.getLoginUser();
        //根据用户id查询权限信息
        List<String> perms = menuService.selectPermsByUserId(loginUser.getUser().getId());
        //根据用户id查询角色信息
        List<String> roleKeys = roleService.selectRoleKeyByUserId(loginUser.getUser().getId());
        // 获取用户信息
        UserInfoVo userInfoVo = BeanCopyUtils.copyBean(loginUser.getUser(), UserInfoVo.class);
        //封装数据返回
        AdminUserInfoVo adminUserInfoVo = new AdminUserInfoVo(perms, roleKeys, userInfoVo);
        return ResponseResult.okResult(adminUserInfoVo);
    }
}
