package com.leo23.service.impl;

import com.leo23.utils.SecurityUtils;
import org.springframework.stereotype.Service;

import java.util.List;

@Service(value = "ps")
public class PermissionServiceImpl {
    /**
     * 判断当前用户是否具有permission
     *
     * @param permission
     * @return
     */
    public boolean hasPermission(String permission) {
        // 超级管理员直接返回true
        if (SecurityUtils.isAdmin()) {
            return true;
        }
        // 否则获取当前用户所具有的权限列表，判断是否存在permission
        List<String> permissions = SecurityUtils.getLoginUser().getPermissions();
        return permissions.contains(permission);
    }
}
