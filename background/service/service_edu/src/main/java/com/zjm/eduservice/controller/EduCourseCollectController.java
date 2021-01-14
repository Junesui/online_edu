package com.zjm.eduservice.controller;


import com.zjm.commonutils.result.R;
import com.zjm.commonutils.util.JwtInfo;
import com.zjm.commonutils.util.JwtUtils;
import com.zjm.eduservice.entity.EduCourseCollect;
import com.zjm.eduservice.entity.vo.CourseCollectVo;
import com.zjm.eduservice.entity.vo.CourseInfoVo;
import com.zjm.eduservice.service.EduCourseCollectService;
import com.zjm.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;

/**
 * 课程收藏 前端控制器
 * @author ZengJinming
 * @since 2020-05-22
 */
@Api(description="课程收藏相关")
@RestController
@RequestMapping("/eduservice/edu-course-collect")
@Slf4j
public class EduCourseCollectController {
    @Autowired
    private EduCourseCollectService courseCollectService;

    //1.收藏课程  添加一条收藏的记录
    @ApiOperation(value = "添加收藏课程")
    @PostMapping("addCourseCollect/{courseId}")
    public R addCourseCollect(@ApiParam(name = "courseId", value = "课程id", required = true)
                              @PathVariable String courseId, HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        courseCollectService.saveCourseCollect(courseId, jwtInfo.getId());
        return R.success();
    }

    //2.获取课程收藏列表
    @ApiOperation(value = "获取课程收藏列表")
    @GetMapping("courseCollectList")
    public R showCourseCollect(HttpServletRequest request) {
        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        List<CourseCollectVo> list = courseCollectService.selectListByMemberId(jwtInfo.getId());
        return R.success().data("items", list);
    }

    //3.取消收藏  删除一条收藏的记录
    @ApiOperation(value = "取消收藏课程")
    @DeleteMapping("remove/{courseId}")
    public R removeCourseCollect(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean result = courseCollectService.removeCourseCollect(courseId, jwtInfo.getId());
        if (result) {
            return R.success().message("已取消收藏");
        } else {
            return R.error().message("取消收藏失败");
        }
    }

    //4.判断是否收藏
    @ApiOperation(value = "判断是否收藏")
    @GetMapping("is-collect/{courseId}")
    public R isCollect(
            @ApiParam(name = "courseId", value = "课程id", required = true)
            @PathVariable String courseId,
            HttpServletRequest request) {

        JwtInfo jwtInfo = JwtUtils.getMemberIdByJwtToken(request);
        boolean isCollect = courseCollectService.isCollect(courseId, jwtInfo.getId());
        return R.success().data("isCollect", isCollect);
    }
}

