package com.zjm.eduservice.client.fallback;

import com.zjm.eduservice.client.OrdersClient;
import org.springframework.stereotype.Component;

@Component
public class OrdersFileDegradeFeignClient implements OrdersClient {
    @Override
    public boolean isBoughtCourse(String courseId, String memberId) {
        return false;
    }
    //出错之后会执行

}
