package com.zjm.oss.controller;

import com.zjm.commonutils.result.R;
import com.zjm.oss.service.OssService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 上传文件部分
 * @author ZengJinming
 * @time 2020-04-03
 */

@Api(description="上传文件")
@RestController
@RequestMapping("/eduoss/fileoss")
//@CrossOrigin
public class OssController {
    @Autowired
    private OssService ossService;

    @ApiOperation(value = "上传头像的方法")
    //上传头像的方法
    @PostMapping
    public R uploadOssFile(MultipartFile file) {
        //获取上传文件  MultipartFile
        //返回上传到oss的路径
        String url = ossService.uploadFileAvatar(file);
        return R.success().data("url",url);
    }

    @ApiOperation(value = "文件删除")
    @DeleteMapping("remove")
    public R removeFile(
            @ApiParam(value = "要删除的文件url路径", required = true)
            @RequestBody String url){
        ossService.removeFile(url);
        return R.success().message("文件删除成功");
    }

}
