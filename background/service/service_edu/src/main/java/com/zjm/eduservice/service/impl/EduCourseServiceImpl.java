package com.zjm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.eduservice.entity.EduCourse;
import com.zjm.eduservice.entity.EduCourseDescription;
import com.zjm.eduservice.entity.frontvo.CourseFrontVo;
import com.zjm.eduservice.entity.frontvo.CourseWebVo;
import com.zjm.eduservice.entity.vo.CourseInfoVo;
import com.zjm.eduservice.entity.vo.CoursePublishVo;
import com.zjm.eduservice.mapper.EduCourseMapper;
import com.zjm.eduservice.service.EduChapterService;
import com.zjm.eduservice.service.EduCourseDescriptionService;
import com.zjm.eduservice.service.EduCourseService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.zjm.eduservice.service.EduVideoService;
import com.zjm.servicebase.exceptionhandler.MSException;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 课程 服务实现类
 * @author ZengJinming
 * @since 2020-04-04
 */
@Service
public class EduCourseServiceImpl extends ServiceImpl<EduCourseMapper, EduCourse> implements EduCourseService {

    //课程描述service注入
    @Autowired
    private EduCourseDescriptionService eduCourseDescriptionService;

    //注入小节和章节service
    @Autowired
    private EduVideoService eduVideoService;

    @Autowired
    private EduChapterService eduChapterService;

    //添加课程基本信息的方法
    @Override
    public String saveCourseInfo(CourseInfoVo courseInfoVo) {
        //1.向课程表添加课程基本信息
        //CourseInfoVo对象转换eduCourse对象
        EduCourse eduCourse = new EduCourse();
        //courseInfoVo值复制到对应eduCourse对象里面
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        //定义insert值判断添加成功失败
        int insert = baseMapper.insert(eduCourse);
        if(insert == 0) {
            //添加失败
            throw new MSException(20001,"添加课程信息失败");
        }

        //获取添加之后课程id
        String cid = eduCourse.getId();

        //2.向课程简介表添加课程简介
        EduCourseDescription eduCourseDescription = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,eduCourseDescription);
        //eduCourseDescription.setDescription(courseInfoVo.getDescription());
        //设置描述id就是课程id
        eduCourseDescription.setId(cid);
        eduCourseDescriptionService.save(eduCourseDescription);
        return cid;
    }

    //根据课程id查询课程基本信息 回显
    @Override
    public CourseInfoVo getCourseInfo(String courseId) {
        //1 查询课程表
        EduCourse eduCourse = baseMapper.selectById(courseId);
        CourseInfoVo courseInfoVo = new CourseInfoVo();
        BeanUtils.copyProperties(eduCourse,courseInfoVo);

        //2 查询描述表
        EduCourseDescription courseDescription = eduCourseDescriptionService.getById(courseId);
        BeanUtils.copyProperties(courseDescription,courseInfoVo);

        return courseInfoVo;
    }

    //修改课程信息
    @Override
    public void updateCourseInfo(CourseInfoVo courseInfoVo) {
        //1 修改课程表
        EduCourse eduCourse = new EduCourse();
        BeanUtils.copyProperties(courseInfoVo,eduCourse);
        int update = baseMapper.updateById(eduCourse);
        if(update == 0) {
            throw new MSException(20001,"修改课程信息失败");
        }

        //2 修改描述表
        EduCourseDescription description = new EduCourseDescription();
        BeanUtils.copyProperties(courseInfoVo,description);
        eduCourseDescriptionService.updateById(description);
    }

    //根据课程id查询课程确认信息
    @Override
    public CoursePublishVo publishCourseInfo(String id) {
        //调用mapper接口方法
        CoursePublishVo publishCourseInfo = baseMapper.getPublishCourseInfo(id);
        return publishCourseInfo;

    }

    //删除课程
    @Override
    public void removeCourse(String courseId) {
        //1 根据课程id删除小节
        eduVideoService.removeVideoByCourseId(courseId);

        //2 根据课程id删除章节
        eduChapterService.removeChapterByCourseId(courseId);

        //3 根据课程id删除描述
        eduCourseDescriptionService.removeById(courseId);

        //4 根据课程id删除课程本身
        int result = baseMapper.deleteById(courseId);
        if(result == 0) { //失败返回
            throw new MSException(20001,"删除失败");
        }
    }

    //查询前8条热门课程
    @Cacheable(value = "banner", key = "'selectCourseList'")
    @Override
    public List<EduCourse> selectHotCourse() {
        QueryWrapper<EduCourse> wrapperCourse = new QueryWrapper<>();
        wrapperCourse.orderByDesc("id");
        wrapperCourse.last("limit 8");
        List<EduCourse> List = baseMapper.selectList(wrapperCourse);
        return List;
    }

    //条件查询带分页查询课程
    @Override
    public Map<String, Object> getCourseFrontList(Page<EduCourse> pageCourse, CourseFrontVo courseFrontVo) {
        // 根据讲师id查询所讲课程
        QueryWrapper<EduCourse> wrapper = new QueryWrapper<>();
        //判断条件值是否为空，不为空拼接
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectParentId())) { //一级分类
            wrapper.eq("subject_parent_id",courseFrontVo.getSubjectParentId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getSubjectId())) { //二级分类
            wrapper.eq("subject_id",courseFrontVo.getSubjectId());
        }
        if(!StringUtils.isEmpty(courseFrontVo.getBuyCountSort())) { //关注度
            wrapper.orderByDesc("buy_count");
        }
        if (!StringUtils.isEmpty(courseFrontVo.getGmtCreateSort())) { //最新
            wrapper.orderByDesc("gmt_create");
        }

        if (!StringUtils.isEmpty(courseFrontVo.getPriceSort())) {//价格
            wrapper.orderByDesc("price");
        }
        baseMapper.selectPage(pageCourse,wrapper);

        List<EduCourse> records = pageCourse.getRecords();
        long current = pageCourse.getCurrent();
        long pages = pageCourse.getPages();
        long size = pageCourse.getSize();
        long total = pageCourse.getTotal();
        boolean hasNext = pageCourse.hasNext();//下一页
        boolean hasPrevious = pageCourse.hasPrevious();//上一页

        //把分页数据获取出来，放到map集合
        Map<String, Object> map = new HashMap<>();
        map.put("items", records);
        map.put("current", current);
        map.put("pages", pages);
        map.put("size", size);
        map.put("total", total);
        map.put("hasNext", hasNext);
        map.put("hasPrevious", hasPrevious);

        //map返回
        return map;
    }

    //根据课程id，查询课程信息，更新浏览量
    @Transactional(rollbackFor = Exception.class)
    @Override
    public CourseWebVo getBaseCourseInfo(String courseId) {
        EduCourse course = baseMapper.selectById(courseId);
        //更新浏览数
        course.setViewCount(course.getViewCount() + 1);
        baseMapper.updateById(course);
        //获取课程信息
        return baseMapper.getBaseCourseInfo(courseId);
    }

    //支付后，更新课程销量
    @Override
    public void updateBuyCountById(String id) {
        EduCourse course = baseMapper.selectById(id);
        course.setBuyCount(course.getBuyCount() + 1);
        this.updateById(course);
    }


}
