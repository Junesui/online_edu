package com.zjm.eduservice.mapper;

import com.zjm.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.eduservice.entity.frontvo.CourseWebVo;
import com.zjm.eduservice.entity.vo.CoursePublishVo;

/**
 * 课程 Mapper 接口
 * @author ZengJinming
 * @since 2020-04-04
 */
public interface EduCourseMapper extends BaseMapper<EduCourse> {

    CoursePublishVo getPublishCourseInfo(String courseId);

    //根据课程id，查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);
}
