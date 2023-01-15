package com.leo23.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.leo23.domain.entity.UserRole;

import java.util.List;

/**
 * 用户和角色关联表(UserRole)表服务接口
 *
 * @author makejava
 * @since 2023-01-15 20:45:26
 */
public interface UserRoleService extends IService<UserRole> {
    // 先去user_role表删除user_id相同字段的数据，再新增对应的role_id
    void saveUserRole(Long id, List<String> roleIds);
}

