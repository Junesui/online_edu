package com.zjm.eduservice.controller;


import com.zjm.commonutils.result.R;
import com.zjm.eduservice.client.VodClient;
import com.zjm.eduservice.entity.EduVideo;
import com.zjm.eduservice.service.EduVideoService;
import com.zjm.servicebase.exceptionhandler.MSException;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

/**
 * 课程视频 前端控制器
 * @author ZengJinming
 * @since 2020-04-04
 */
@Api(description="小节部分")
@RestController
@RequestMapping("/eduservice/edu-video")
//@CrossOrigin
public class EduVideoController {
    @Autowired
    private EduVideoService videoService;

    //注入vodClient
    @Autowired
    private VodClient vodClient;

    //添加小节
    @ApiOperation(value = "添加小节")
    @PostMapping("addVideo")
    public R addVideo(@RequestBody EduVideo eduVideo) {
        videoService.save(eduVideo);
        return R.success();
    }

    //删除小节，同时把里面视频删除
    @ApiOperation(value = "删除小节")
    @DeleteMapping("{id}")
    public R deleteVideo(@PathVariable String id) {
        //根据小节id获取视频id，调用方法实现视频删除
        EduVideo eduVideo = videoService.getById(id);
        String videoSourceId = eduVideo.getVideoSourceId();
        //判断小节里面是否有视频id
        if(!StringUtils.isEmpty(videoSourceId)) {
            //根据视频id，远程调用实现视频删除
            R result = vodClient.removeAlyVideo(videoSourceId);
            if(result.getCode() == 20001) {
                throw new MSException(20001,"删除视频失败，熔断器...");
            }
        }
        //删除小节
        videoService.removeById(id);
        return R.success();
    }

    //根据小节id查询
    @ApiOperation(value = "根据小节id查询")
    @GetMapping("getVideoInfo/{videoId}")
    public R getChapterInfo(@PathVariable String videoId) {
        EduVideo eduVideo = videoService.getById(videoId);
        if (eduVideo != null) {
            return R.success().data("video",eduVideo);
        } else {
            return R.error().message("数据不存在");
        }

    }

    //修改小节
    @ApiOperation(value = "修改小节")
    @PostMapping("updateVideo")
    public R updateChapter(@RequestBody EduVideo eduVideo) {
        videoService.updateById(eduVideo);
        return R.success();
    }
}

