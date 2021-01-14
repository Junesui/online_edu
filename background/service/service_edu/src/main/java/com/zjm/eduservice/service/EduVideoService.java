package com.zjm.eduservice.service;

import com.zjm.eduservice.entity.EduVideo;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 * 课程视频 服务类
 * </p>
 *
 * @author testjava
 * @since 2020-04-04
 */
public interface EduVideoService extends IService<EduVideo> {

    //根据课程id删除小节
    void removeVideoByCourseId(String courseId);
}
