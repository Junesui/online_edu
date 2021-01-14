package com.zjm.eduservice.service;

import com.zjm.eduservice.entity.EduChapter;
import com.baomidou.mybatisplus.extension.service.IService;
import com.zjm.eduservice.entity.chapter.ChapterVo;

import java.util.List;

/**
 * 课程 服务类
 * @author ZengJinming
 * @since 2020-04-04
 */
public interface EduChapterService extends IService<EduChapter> {

    //课程大纲列表,根据课程id进行查询
    List<ChapterVo> getChapterVideoByCourseId(String courseId);

    //删除章节的方法
    boolean deleteChapter(String chapterId);

    //根据课程id删除章节
    void removeChapterByCourseId(String courseId);
}
