package com.zjm.eduservice.client.fallback;

import com.zjm.commonutils.ordervo.UcenterMemberOrder;
import com.zjm.eduservice.client.UcenterClient;
import org.springframework.stereotype.Component;

@Component
public class UcenterFileDegradeFeignClient implements UcenterClient {
    @Override
    public UcenterMemberOrder getUserInfoOrder(String id) {
        return null;
    }
}
