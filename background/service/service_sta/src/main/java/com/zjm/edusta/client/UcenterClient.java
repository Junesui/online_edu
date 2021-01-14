package com.zjm.edusta.client;

import com.zjm.commonutils.result.R;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "service-ucenter",fallback = UcenterFileDegradeFeignClient.class) //调用的服务名称
@Component
public interface UcenterClient {
    //查询某天注册人数
    @GetMapping("/eduucenter/ucenter-member/countRegister/{day}")
    public R countRegister(@PathVariable("day") String day);
}
