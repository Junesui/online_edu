package com.zjm.eduservice.entity.vo;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class CourseCollectVo {
    private static final long serialVersionUID = 1L;

    @ApiModelProperty(value = "ID")
    @TableId(value = "id", type = IdType.ID_WORKER_STR)
    private String id; //id

    @ApiModelProperty(value = "课程ID")
    private String courseId; //课程id

    @ApiModelProperty(value = "课程标题")
    private String title;// 课程标题

    @ApiModelProperty(value = "价格")
    // BigDecimal精确价格到分
    private BigDecimal price;

    @ApiModelProperty(value = "课时数")
    private Integer lessonNum; //课时数

    @ApiModelProperty(value = "课程封面")
    private String cover; //课程封面

    @ApiModelProperty(value = "收藏时间")
    @TableField(fill = FieldFill.INSERT)
    private String gmtCreate; //收藏时间

    @ApiModelProperty(value = "讲师姓名")
    private String teacherName; //讲师姓名
}
