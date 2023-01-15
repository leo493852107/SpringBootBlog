package com.leo23.domain.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class UpdateRoleMenuDto extends RoleMenuDto {
    private Long id;
    private String remark;
}
