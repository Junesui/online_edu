package com.zjm.educms.feign;


import com.zjm.commonutils.result.R;
import com.zjm.educms.feign.fallback.OssFileServiceFallBack;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.RequestBody;

/**
 * @author zjm
 * @since 2020/4/28
 */
@Service
@FeignClient(value = "service-oss", fallback = OssFileServiceFallBack.class)
public interface OssFileService {

    @DeleteMapping("/eduoss/fileoss/remove")
    R removeFile(@RequestBody String url);
}