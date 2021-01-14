package com.zjm.eduorder.client.fallback;

import com.zjm.commonutils.ordervo.CourseWebVoOrder;
import com.zjm.commonutils.result.R;
import com.zjm.eduorder.client.EduClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class EduClientFallBack implements EduClient {
    @Override
    public CourseWebVoOrder getCourseInfoOrder(String id) {
        log.error("熔断器被执行");
        return null;
    }

    public R updateBuyCountById(String id) {
        log.error("熔断器被执行");
        return R.error();
    }
}
