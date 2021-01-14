package com.zjm.edusta.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.zjm.commonutils.result.R;
import com.zjm.edusta.client.UcenterClient;
import com.zjm.edusta.entity.StatisticsDaily;
import com.zjm.edusta.mapper.StatisticsDailyMapper;
import com.zjm.edusta.service.StatisticsDailyService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 网站统计日数据 服务实现类
 * @author ZengJinming
 * @since 2020-04-13
 */
@Service
public class StatisticsDailyServiceImpl extends ServiceImpl<StatisticsDailyMapper, StatisticsDaily> implements StatisticsDailyService {

    @Autowired
    private UcenterClient ucenterClient;

    //统计某一天的注册人数,生成统计数据
    @Override
    public void registerCount(String day) {
        //相同日期时  先删除旧数据  再添加
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.eq("date_calculated",day);
        baseMapper.delete(wrapper);
        //远程调用得到某一天的注册人数
        R register = ucenterClient.countRegister(day);
        Integer countRegister = (Integer)register.getData().get("countRegister");

        //把获取到的数据添加到数据库 统计分析表内
        StatisticsDaily statisticsDaily = new StatisticsDaily();
        statisticsDaily.setRegisterNum(countRegister); //注册人数
        statisticsDaily.setDateCalculated(day); //统计日期
        statisticsDaily.setVideoViewNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setLoginNum(RandomUtils.nextInt(100,200));
        statisticsDaily.setCourseNum(RandomUtils.nextInt(100,200));
        baseMapper.insert(statisticsDaily);

    }

    //图表显示 返回两部分数据：日期json数组、数量json数组
    @Override
    public Map<String, Object> getShowData(String type, String begin, String end) {
        //根据条件查询对应的数据
        QueryWrapper<StatisticsDaily> wrapper = new QueryWrapper<>();
        wrapper.between("date_calculated",begin,end);
        wrapper.select("date_calculated",type);
        List<StatisticsDaily> statisticsDailies = baseMapper.selectList(wrapper);

        //返回的数据：1.日期 2.日期对应的结构
        //前端json数组对应后端的list集合
        //创建日期list 数量list
        List<String> date_calculatedList = new ArrayList<>();
        List<Integer> numDataList = new ArrayList<>();

        //遍历查询所有数据的list集合statisticsDailies 进行封装
        for (int i = 0; i < statisticsDailies.size(); i++) {
            StatisticsDaily daily = statisticsDailies.get(i);
            //封装日期list集合
            date_calculatedList.add(daily.getDateCalculated());
            //封装对应的具体数量（登录 注册 播放等）
            switch (type){
                case "login_num":
                    numDataList.add(daily.getLoginNum());
                    break;
                case "register_num":
                    numDataList.add(daily.getRegisterNum());
                    break;
                case "video_view_num":
                    numDataList.add(daily.getVideoViewNum());
                    break;
                case "course_num":
                    numDataList.add(daily.getCourseNum());
                    break;
                default:
                    break;
            }
        }

        //把封装之后两个list集合放到map集合，进行返回
        Map<String, Object> map = new HashMap<>();
        map.put("date_calculatedList",date_calculatedList);
        map.put("numDataList",numDataList);
        return map;
    }
}
