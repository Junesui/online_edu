package com.zjm.eduservice.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.zjm.eduservice.entity.EduTeacher;
import com.zjm.eduservice.mapper.EduTeacherMapper;
import com.zjm.eduservice.service.EduTeacherService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 讲师 服务实现类
 * @author ZengJinming
 * @time 2020-03-29
 */
@Service
public class EduTeacherServiceImpl extends ServiceImpl<EduTeacherMapper, EduTeacher> implements EduTeacherService {

    //查询前4条名师
    @Cacheable(value = "banner", key = "'selectTeacherList'")
    @Override
    public List<EduTeacher> selectHotTeacher() {
        QueryWrapper<EduTeacher> wrapperTeacher = new QueryWrapper<>();
        wrapperTeacher.orderByAsc("sort");
        wrapperTeacher.last("limit 4");
        List<EduTeacher> List = baseMapper.selectList(wrapperTeacher);
        return List;
    }

    //1 讲师页 分页查询讲师的方法
    @Override
    public Map<String, Object> getTeacherFrontList(Page<EduTeacher> pageTeacher) {
        QueryWrapper<EduTeacher> wrapper = new QueryWrapper<>();
        wrapper.orderByDesc("id");
        //把分页数据封装到pageTeacher对象里去
        baseMapper.selectPage(pageTeacher,wrapper);

        List<EduTeacher> records = pageTeacher.getRecords();
        long current = pageTeacher.getCurrent();
        long pages = pageTeacher.getPages();
        long size = pageTeacher.getSize();
        long total = pageTeacher.getTotal();
        boolean hasNext = pageTeacher.hasNext();//下一页
        boolean hasPrevious = pageTeacher.hasPrevious();//上一页

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
}
