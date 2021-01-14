package com.zjm.vod.controller;

import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthRequest;
import com.aliyuncs.vod.model.v20170321.GetVideoPlayAuthResponse;
import com.zjm.commonutils.result.R;
import com.zjm.servicebase.exceptionhandler.MSException;
import com.zjm.vod.service.VodService;
import com.zjm.vod.utils.ConstantVodUtils;
import com.zjm.vod.utils.InitVodClient;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Api(description="上传视频")
@RestController
@RequestMapping("/eduvod/video")
//@CrossOrigin
public class VodController {
    @Autowired
    private VodService vodService;

    //上传视频到阿里云
    @ApiOperation(value = "上传视频到阿里云")
    @PostMapping("uploadAlyVideo")
    public R uploadAlyVideo(MultipartFile file) {
        //返回上传视频id
        String videoId = vodService.uploadVideoAly(file);
        return R.success().data("videoId",videoId);
    }

    //根据视频id删除阿里云视频
    @ApiOperation(value = "根据视频id删除阿里云视频")
    @DeleteMapping("removeAlyVideo/{id}")
    public R removeAlyVideo(@PathVariable String id) {
        vodService.removeAlyVideo(id);
        return R.success();
    }

    //删除多个阿里云视频的方法
    //参数多个视频id  List videoIdList
    @ApiOperation(value = "删除多个阿里云视频")
    @DeleteMapping("delete-batch")
    public R deleteBatch(@RequestParam("videoIdList") List<String> videoIdList) {
        vodService.removeMoreAlyVideo(videoIdList);
        return R.success();
    }

    //根据视频id获取视频凭证
    @GetMapping("getPlayAuth/{id}")
    public R getPlayAuth(@PathVariable String id) {
        try {
            //创建初始化对象
            DefaultAcsClient client =
                    InitVodClient.initVodClient(ConstantVodUtils.ACCESS_KEY_ID, ConstantVodUtils.ACCESS_KEY_SECRET);
            //创建获取凭证request和response对象
            GetVideoPlayAuthRequest request = new GetVideoPlayAuthRequest();
            //向request设置视频id
            request.setVideoId(id);
            //调用方法得到凭证
            GetVideoPlayAuthResponse response = client.getAcsResponse(request);
            String playAuth = response.getPlayAuth();
            return R.success().data("playAuth",playAuth);
        }catch(Exception e) {
            throw new MSException(20001,"获取凭证失败");
        }
    }

}



