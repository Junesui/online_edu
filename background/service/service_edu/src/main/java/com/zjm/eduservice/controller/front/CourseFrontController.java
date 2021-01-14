package com.zjm.eduservice.controller.front;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.commonutils.util.JwtInfo;
import com.zjm.commonutils.util.JwtUtils;
import com.zjm.commonutils.result.R;
import com.zjm.commonutils.ordervo.CourseWebVoOrder;
import com.zjm.eduservice.client.OrdersClient;
import com.zjm.eduservice.entity.EduCourse;
import com.zjm.eduservice.entity.chapter.ChapterVo;
import com.zjm.eduservice.entity.frontvo.CourseFrontVo;
import com.zjm.eduservice.entity.frontvo.CourseWebVo;
import com.zjm.eduservice.service.EduChapterService;
import com.zjm.eduservice.service.EduCourseService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.List;
import java.util.Map;

@Api(description="课程页功能")
@RestController
@RequestMapping("/eduservice/coursefront")
//@CrossOrigin
public class CourseFrontController {

    @Autowired
    private EduCourseService courseService;

    @Autowired
    private EduChapterService chapterService;

    @Autowired
    private OrdersClient ordersClient;

    //1 条件查询带分页查询课程
    @ApiOperation(value = "条件查询带分页查询课程")
    @PostMapping("getFrontCourseList/{page}/{limit}")
    public R getFrontCourseList(@PathVariable long page, @PathVariable long limit,
                                @RequestBody(required = false) CourseFrontVo courseFrontVo) {
        Page<EduCourse> pageCourse = new Page<>(page,limit);
        Map<String,Object> map = courseService.getCourseFrontList(pageCourse,courseFrontVo);
        //返回分页所有数据
        return R.success().data(map);
    }

    //2 课程详情的方法
    @ApiOperation(value = " 课程详情的方法")
    @GetMapping("getFrontCourseInfo/{courseId}")
    public R getFrontCourseInfo(@PathVariable String courseId, HttpServletRequest request) {
        //根据课程id，编写sql语句查询课程信息
        CourseWebVo courseWebVo = courseService.getBaseCourseInfo(courseId);
        //根据课程id查询章节和小节
        List<ChapterVo> chapterVideoList = chapterService.getChapterVideoByCourseId(courseId);
        return R.success().data("courseWebVo",courseWebVo).data("chapterVideoList",chapterVideoList);
    }

    //根据课程id查询课程信息
    @ApiOperation(value = " 根据课程id查询课程信息")
    @PostMapping("getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable String id) {
        CourseWebVo courseInfo = courseService.getBaseCourseInfo(id);
        CourseWebVoOrder courseWebVoOrder = new CourseWebVoOrder();
        BeanUtils.copyProperties(courseInfo,courseWebVoOrder);
        return courseWebVoOrder;
    }
}












