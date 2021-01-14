package com.zjm.edusta.controller;


import com.zjm.commonutils.result.R;
import com.zjm.edusta.service.StatisticsDailyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

/**
 * 网站统计日数据 前端控制器
 * @author ZengJinming
 * @since 2020-04-13
 */
@Api(description="统计")
@RestController
@RequestMapping("/edusta/statistics")
//@CrossOrigin
public class StatisticsDailyController {
    @Autowired
    private StatisticsDailyService statisticsDailyService;

    //统计某一天的注册人数,生成统计数据
    @ApiOperation(value = "统计某一天的注册人数")
    @PostMapping("registerCount/{day}")
    public R registerCount(@PathVariable String day){
        statisticsDailyService.registerCount(day);
        return R.success();
    }

    //图表显示 返回两部分数据：日期json数组、数量json数组
    @ApiOperation(value = "图表显示")
    @GetMapping("showData/{type}/{begin}/{end}")
    public R showData(@PathVariable String type,@PathVariable String begin,
                      @PathVariable String end){
        Map<String, Object> map = statisticsDailyService.getShowData(type,begin,end);
        return R.success().data(map);
    }

}

