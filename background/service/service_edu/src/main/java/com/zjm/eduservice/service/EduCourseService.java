package com.zjm.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.eduservice.entity.EduCourse;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjm.eduservice.entity.frontvo.CourseFrontVo;
import com.zjm.eduservice.entity.frontvo.CourseWebVo;
import com.zjm.eduservice.entity.vo.CourseInfoVo;
import com.zjm.eduservice.entity.vo.CoursePublishVo;

import java.util.List;
import java.util.Map;

/**
 * 课程 服务类
 * @author ZengJinming
 * @since 2020-04-04
 */
public interface EduCourseService extends IService<EduCourse> {

    //添加课程基本信息的方法
    String saveCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程基本信息 回显
    CourseInfoVo getCourseInfo(String courseId);

    //修改课程信息
    void updateCourseInfo(CourseInfoVo courseInfoVo);

    //根据课程id查询课程确认信息
    CoursePublishVo publishCourseInfo(String id);

    //删除课程
    void removeCourse(String courseId);

    //查询前8条热门课程
    List<EduCourse> selectHotCourse();

    //条件查询带分页查询课程
    Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo);

    //根据课程id，查询课程信息
    CourseWebVo getBaseCourseInfo(String courseId);

    //支付后，更新课程销量
    void updateBuyCountById(String id);
}
