package com.zjm.eduservice.controller;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.commonutils.result.R;
import com.zjm.eduservice.entity.EduCourse;
import com.zjm.eduservice.entity.vo.CourseInfoVo;
import com.zjm.eduservice.entity.vo.CoursePublishVo;
import com.zjm.eduservice.entity.vo.CourseQuery;
import com.zjm.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 课程 前端控制器
 * @author Zengjinming
 * @since 2020-04-04
 */

@Api(description="课程基本信息")
@RestController
@RequestMapping("/eduservice/edu-course")
//@CrossOrigin
public class EduCourseController {
    @Autowired
    private EduCourseService courseService;

    //添加课程基本信息
    @ApiOperation(value = "添加课程基本信息")
    @PostMapping("addCourseInfo")
    public R addCourseInfo(@RequestBody CourseInfoVo courseInfoVo){
        //返回添加之后课程id，为了第二个页面添加课程大纲使用
        String id = courseService.saveCourseInfo(courseInfoVo);
        return R.success().data("courseId",id);
    }

    //根据课程id查询课程基本信息 回显
    @ApiOperation(value = "根据课程id查询课程基本信息")
    @GetMapping("getCourseInfo/{courseId}")
    public R getCourseInfo(@PathVariable String courseId) {
        CourseInfoVo courseInfoVo = courseService.getCourseInfo(courseId);
        return R.success().data("courseInfoVo",courseInfoVo);
    }

    //修改课程信息
    @ApiOperation(value = "修改课程信息")
    @PostMapping("updateCourseInfo")
    public R updateCourseInfo(@RequestBody CourseInfoVo courseInfoVo) {
        courseService.updateCourseInfo(courseInfoVo);
        return R.success();
    }

    //根据课程id查询课程确认信息
    @ApiOperation(value = "确认课程信息")
    @GetMapping("getPublishCourseInfo/{id}")
    public R getPublishCourseInfo(@PathVariable String id) {
        CoursePublishVo coursePublishVo = courseService.publishCourseInfo(id);
        return R.success().data("publishCourse",coursePublishVo);
    }

    //课程最终发布 修改课程表中status状态
    @ApiOperation(value = "课程最终发布")
    @PostMapping("publishCourse/{id}")
    public R publishCourse(@PathVariable String id) {
        EduCourse eduCourse = new EduCourse();
        eduCourse.setId(id);
        eduCourse.setStatus("Normal");
        courseService.updateById(eduCourse);
        return R.success();
    }


    //课程列表分页查询显示带条件
    //current 当前页
    //limit 每页记录数
    @ApiOperation(value = "课程列表分页查询显示带条件")
    @PostMapping("pageCourseCondition/{current}/{limit}")
    public R pageCourseCondition(@PathVariable long current, @PathVariable long limit,
                                 @RequestBody(required = false) CourseQuery courseQuery){
        //创建page对象
        Page<EduCourse> pageCourse = new Page<>(current,limit);
        //构建条件
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();

        // 多条件组合查询
        // mybatis学过 动态sql
        String title = courseQuery.getTitle();
        String status = courseQuery.getStatus();

        //判断条件值是否为空，如果不为空拼接条件
        if(!StringUtils.isEmpty(title)) {
            //构建条件
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(status)) {
            wrapper.eq("status",status);
        }

        //排序
        wrapper.orderByDesc("gmt_create");

        //调用方法实现条件查询分页
        courseService.page(pageCourse,wrapper);
        long total = pageCourse.getTotal();//总记录数
        List<EduCourse> records = pageCourse.getRecords(); //数据list集合
        return R.success().data("total",total).data("records",records);
    }

    //删除课程
    @ApiOperation(value = "删除课程")
    @DeleteMapping("deleteCourseById/{courseId}")
    public R deleteCourse(@PathVariable String courseId) {
        courseService.removeCourse(courseId);
        return R.success();
    }

    @ApiOperation("根据课程id更改销售量")
    @GetMapping("updateBuyCount/{id}")
    public R updateBuyCountById(
            @ApiParam(value = "课程id", required = true)
            @PathVariable String id){
        courseService.updateBuyCountById(id);
        return R.success();
    }
}

