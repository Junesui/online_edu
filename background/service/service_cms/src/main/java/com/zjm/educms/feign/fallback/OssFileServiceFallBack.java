package com.zjm.educms.feign.fallback;

import com.zjm.commonutils.result.R;
import com.zjm.educms.feign.OssFileService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @author zjm
 * @since 2020/4/28
 */
@Service
@Slf4j
public class OssFileServiceFallBack implements OssFileService {

    @Override
    public R removeFile(String url) {
        log.info("熔断保护");
        return R.error().message("调用超时");
    }
}