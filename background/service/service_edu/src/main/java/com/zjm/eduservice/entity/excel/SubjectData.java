package com.zjm.eduservice.entity.excel;

import com.alibaba.excel.annotation.ExcelProperty;
import lombok.Data;

@Data
public class SubjectData {

    //1级分类
    @ExcelProperty(index = 0)
    private String oneSubjectName;

    //2级分类
    @ExcelProperty(index = 1)
    private String twoSubjectName;
}
