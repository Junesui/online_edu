package com.zjm.eduservice.mapper;

import com.zjm.eduservice.entity.EduCourseCollect;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.zjm.eduservice.entity.vo.CourseCollectVo;

import java.util.List;

/**
 * <p>
 * 课程收藏 Mapper 接口
 * </p>
 *
 * @author ZengJinming
 * @since 2020-05-22
 */
public interface EduCourseCollectMapper extends BaseMapper<EduCourseCollect> {

    //获取课程收藏列表
    List<CourseCollectVo> selectPageByMemberId(String memberId);
}
