package com.leo23.domain.vo;

import com.leo23.domain.entity.Role;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.Accessors;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
@Accessors(chain = true)
public class UserDetailInfoVo {
    //用户所关联的角色id列表
    private List<Long> roleIds;
    //所有角色的列表
    private List<Role> roles;
    //用户信息
    private UserVo user;
}
