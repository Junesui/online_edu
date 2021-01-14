package com.zjm.eduservice.entity.vo;

import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
//课程列表条件查询
@Data
public class CourseQuery {
        @ApiModelProperty(value = "课程名称,模糊查询")
        private String title;

        @ApiModelProperty(value = "状态 Draft未发布 Normal已发布")
        private String status;

}
