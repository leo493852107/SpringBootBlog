package com.leo23.domain.vo;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoleMenuVo {
    private List<MenuVo> menus;
    private List<Long> checkedKeys;
}
