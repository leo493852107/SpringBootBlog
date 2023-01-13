package com.leo23.domain.vo;


import com.leo23.domain.entity.Menu;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class RoutersVo {
    private List<Menu> menus;
}
