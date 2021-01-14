package com.zjm.eduorder.client;

import com.zjm.commonutils.ordervo.UcenterMemberOrder;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Component
@FeignClient("service-ucenter")
public interface UcenterClient {

    //根据用户id获取用户信息
    @PostMapping("/eduucenter/ucenter-member/getUserInfoOrder/{id}")
    public UcenterMemberOrder getUserInfoOrder(@PathVariable("id") String id);
}
