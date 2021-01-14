package com.zjm.eduservice.service;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.eduservice.entity.EduTeacher;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;
import java.util.Map;

/**
 * 讲师 服务类
 * @author ZengJinming
 * @time 2020-03-29
 */
public interface EduTeacherService extends IService<EduTeacher> {

    //查询前4条名师
    List<EduTeacher> selectHotTeacher();

    //1  讲师页  分页查询讲师的方法
    Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher);
}
