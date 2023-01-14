package com.leo23.domain.vo;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
public class ExcelCategoryVo {
    @ExcelProperty("分类名")
    private String name;
    // 描述
    @ExcelProperty("描述")
    private String description;
    @ExcelProperty("状态0：正常，1：禁用")
    private String status;
}
