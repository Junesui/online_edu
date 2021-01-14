package com.zjm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.eduservice.entity.EduCourseCollect;
import com.zjm.eduservice.entity.vo.CourseCollectVo;
import com.zjm.eduservice.mapper.EduCourseCollectMapper;
import com.zjm.eduservice.service.EduCourseCollectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 课程收藏 服务实现类
 * </p>
 *
 * @author ZengJinming
 * @since 2020-05-22
 */
@Service
public class EduCourseCollectServiceImpl extends ServiceImpl<EduCourseCollectMapper, EduCourseCollect> implements EduCourseCollectService {

    //判断是否收藏
    @Override
    public boolean isCollect(String courseId, String memberId) {
        QueryWrapper<EduCourseCollect> queryWrapper = new QueryWrapper<>();
        queryWrapper
                .eq("course_id", courseId)
                .eq("member_id", memberId);
        Integer count = baseMapper.selectCount(queryWrapper);
        return count > 0;
    }

    //收藏课程  添加一条收藏的记录
    @Override
    public void saveCourseCollect(String courseId, String memberId) {
        //未收藏则收藏
        if(!this.isCollect(courseId, memberId)) {
            EduCourseCollect courseCollect = new EduCourseCollect();
            courseCollect.setCourseId(courseId);
            courseCollect.setMemberId(memberId);
            this.save(courseCollect);
        }
    }

    //获取课程收藏列表
    @Override
    public List<CourseCollectVo> selectListByMemberId(String memberId) {
        return baseMapper.selectPageByMemberId(memberId);
    }

    @Override
    public boolean removeCourseCollect(String courseId, String memberId) {
        //已收藏则删除
        if(this.isCollect(courseId, memberId)) {
            QueryWrapper<EduCourseCollect> queryWrapper = new QueryWrapper<>();
            queryWrapper.eq("course_id", courseId).eq("member_id", memberId);
            return this.remove(queryWrapper);
        }
        return false;
    }
}
