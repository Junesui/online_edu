package com.zjm.eduservice.entity.vo;

import lombok.Data;

//封装发布课程第三步显示的数据
@Data
public class CoursePublishVo {
       private String id;
    private String title;
    private String cover;
    private Integer lessonNum;
    private String subjectLevelOne;
    private String subjectLevelTwo;
    private String teacherName;
    private String price;//只用于显示
}
