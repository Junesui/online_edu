package com.zjm.eduorder.client;

import com.zjm.commonutils.ordervo.CourseWebVoOrder;
import com.zjm.commonutils.result.R;
import com.zjm.eduorder.client.fallback.EduClientFallBack;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component

@FeignClient(value = "service-edu",fallback = EduClientFallBack.class)//调用的服务名称
public interface EduClient {

    //根据课程id查询课程信息
    @PostMapping("/eduservice/coursefront/getCourseInfoOrder/{id}")
    public CourseWebVoOrder getCourseInfoOrder(@PathVariable("id") String id);


    //根据课程id更改销售量
    @GetMapping("/eduservice/edu-course/updateBuyCount/{id}")
    public R updateBuyCountById(@PathVariable("id") String id);
}
