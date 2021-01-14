package com.zjm.eduservice.service.impl;

import com.alibaba.excel.EasyExcel;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.eduservice.entity.EduSubject;
import com.zjm.eduservice.entity.excel.SubjectData;
import com.zjm.eduservice.entity.subject.OneSubject;
import com.zjm.eduservice.entity.subject.TwoSubject;
import com.zjm.eduservice.listener.SubjectExcelListener;
import com.zjm.eduservice.mapper.EduSubjectMapper;
import com.zjm.eduservice.service.EduSubjectService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

/**
 * 课程科目 服务实现类
 * @author ZengJinming
 * @since 2020-04-03
 */
@Service
public class EduSubjectServiceImpl extends ServiceImpl<EduSubjectMapper, EduSubject> implements EduSubjectService {

    //添加课程分类
    @Override
    public void saveSubject(MultipartFile file, EduSubjectService eduSubjectService) {
        try {
            //文件输入流
            InputStream in = file.getInputStream();
            //调用方法进行读取
            EasyExcel.read(in, SubjectData.class,new SubjectExcelListener(eduSubjectService)).sheet().doRead();
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    //课程分类列表功能
    @Override
    public List<OneSubject> getAllOneTwoSubject() {
        //1查询所有一级分类 parent_id = 0
        QueryWrapper<EduSubject> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("parent_id","0");
        List<EduSubject> SubjectList1 = baseMapper.selectList(wrapper1);

        //2查询所有二级分类 parent_id! = 0
        QueryWrapper<EduSubject> wrapper2 = new QueryWrapper<>();
        wrapper1.ne("parent_id","0");
        List<EduSubject> SubjectList2 = baseMapper.selectList(wrapper2);

        //创建list集合 存储最终封装数据
        List<OneSubject> finalSubjectList = new ArrayList<>();

        //3封装所有一级分类
        //查询出来所有的一级分类list集合遍历，得到每个一级分类对象，获取每个一级分类对象值，
        //封装到要求的list集合里面 List<OneSubject> finalSubjectList
        for (int i = 0; i < SubjectList1.size(); i++) { //遍历SubjectList1集合
            //得到oneSubjectList每个eduSubject对象
            EduSubject eduSubject = SubjectList1.get(i);
            //把eduSubject里面值获取出来，放到OneSubject对象里面
            OneSubject oneSubject = new OneSubject();
            //eduSubject值复制到对应oneSubject对象里面
            BeanUtils.copyProperties(eduSubject,oneSubject);
            //多个OneSubject放到finalSubjectList里面
            finalSubjectList.add(oneSubject);

            //4封装所有二级分类
            //在一级分类循环遍历查询所有的二级分类
            //创建list集合封装每个一级分类的二级分类
            List<TwoSubject> finalSubjectList2 = new ArrayList<>();
            //遍历二级分类list集合
            for (int m = 0; m < SubjectList2.size(); m++) {
                //获取每个二级分类
                EduSubject eduSubject2 = SubjectList2.get(m);
                //判断二级分类parent_id和一级分类id是否一样  是否是一级分类下的分类
                if(eduSubject2.getParentId().equals(eduSubject.getId())) {
                    //把eduSubject2值复制到TwoSubject里面，放到finalSubjectList2里面
                    TwoSubject twoSubject = new TwoSubject();
                    BeanUtils.copyProperties(eduSubject2,twoSubject);
                    finalSubjectList2.add(twoSubject);
                }
            }
            //最终把一级下面所有二级分类放到一级分类里面 完成封装
            oneSubject.setChildren(finalSubjectList2);
        }

        return finalSubjectList;
    }
}
