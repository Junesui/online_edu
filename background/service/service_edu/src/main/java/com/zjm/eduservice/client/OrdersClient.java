package com.zjm.eduservice.client;

import com.zjm.eduservice.client.fallback.OrdersFileDegradeFeignClient;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-order",fallback = OrdersFileDegradeFeignClient.class) //调用的服务名称
@Component
public interface OrdersClient {
    //根据课程id'和用户id查询订单表中订单的状态 status
    @GetMapping("/eduorder/order/isBought/{courseId}/{memberId}")
    public boolean isBoughtCourse(@PathVariable("courseId") String courseId, @PathVariable("memberId") String memberId);
}
