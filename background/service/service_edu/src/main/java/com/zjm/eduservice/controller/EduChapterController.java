package com.zjm.eduservice.controller;


import com.zjm.commonutils.result.R;
import com.zjm.eduservice.entity.EduChapter;
import com.zjm.eduservice.entity.chapter.ChapterVo;
import com.zjm.eduservice.service.EduChapterService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 * @author ZengJinming
 * @since 2020-04-05
 */
@Api(description="章节部分")
@RestController
@RequestMapping("/eduservice/edu-chapter")
//@CrossOrigin
public class EduChapterController {

    @Autowired
    private EduChapterService eduChapterService;

    //课程大纲列表,根据课程id进行查询
    @ApiOperation(value = "课程大纲列表")
    @GetMapping("getChapterVideo/{courseId}")
    public R getChapterVideo(@PathVariable String courseId) {
        List<ChapterVo> list = eduChapterService.getChapterVideoByCourseId(courseId);
        return R.success().data("allChapterVideo",list);
    }

    //添加章节
    @ApiOperation(value = "添加章节")
    @PostMapping("addChapter")
    public R addChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.save(eduChapter);
        return R.success();
    }

    //根据章节id查询
    @ApiOperation(value = "根据章节id查询")
    @GetMapping("getChapterInfo/{chapterId}")
    public R getChapterInfo(@PathVariable String chapterId) {
        EduChapter eduChapter = eduChapterService.getById(chapterId);
        return R.success().data("chapter",eduChapter);
    }

    //修改章节
    @ApiOperation(value = "修改章节")
    @PostMapping("updateChapter")
    public R updateChapter(@RequestBody EduChapter eduChapter) {
        eduChapterService.updateById(eduChapter);
        return R.success();
    }

    //删除章节
    @ApiOperation(value = "删除章节")
    @DeleteMapping("{chapterId}")
    public R deleteChapter(@PathVariable String chapterId) {
        boolean flag = eduChapterService.deleteChapter(chapterId);
        if(flag) {
            return R.success();
        } else {
            return R.error();
        }

    }
}

