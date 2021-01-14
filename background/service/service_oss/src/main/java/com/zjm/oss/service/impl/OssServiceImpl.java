package com.zjm.oss.service.impl;

import com.aliyun.oss.OSS;
import com.aliyun.oss.OSSClientBuilder;
import com.zjm.oss.service.OssService;
import com.zjm.oss.utils.ConstantPropertiesUtils;
import org.joda.time.DateTime;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.InputStream;
import java.util.UUID;

/**
 * 上传头像服务实现类
 * @author ZengJinming
 * @time 2020-04-03
 */

@Service
public class OssServiceImpl implements OssService {
    //上传头像到oss
    @Override
    public String uploadFileAvatar(MultipartFile file) {
       // 工具类获取值
        String endPoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        try {
            // 创建OSS实例。
            OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

            //获取上传文件输入流
            InputStream inputStream = file.getInputStream();

            //获取文件名称
            String fileName = file.getOriginalFilename();

            //1 在文件名称里面添加随机唯一的值  防止后面上传的覆盖之前的
            //replaceAll去掉uuid的横杠
            String uuid = UUID.randomUUID().toString().replaceAll("-","");
            // yuy76t5rew01.jpg
            fileName = uuid+fileName;

            //2 把文件按照日期进行分类
            //获取当前日期
            //   2020.4.3
            String datePath = new DateTime().toString("yyyy/MM/dd");

            //拼接
            //  2020/4/3/ewtqr313401.jpg
            fileName = datePath+"/"+fileName;

            //调用oss方法实现上传
            //第一个参数  Bucket名称
            //第二个参数  上传到oss文件路径和文件名称   aa/bb/1.jpg
            //第三个参数  上传文件输入流
            ossClient.putObject(bucketName,fileName,inputStream);

            // 关闭OSSClient。
            ossClient.shutdown();

            //把上传之后文件路径返回
            //需要把上传到阿里云oss路径手动拼接出来
            //  https://edu-guli-1010.oss-cn-beijing.aliyuncs.com/01.jpg
            String url = "https://"+bucketName+"."+endPoint+"/"+fileName;
            return url;
        }catch(Exception e) {
            e.printStackTrace();
            return null;
        }
    }

    @Override
    public void removeFile(String url) {
        //读取配置信息
//        String endpoint = ossProperties.getEndpoint();
//        String keyid = ossProperties.getKeyid();
//        String keysecret = ossProperties.getKeysecret();
//        String bucketname = ossProperties.getBucketname();
        String endPoint = ConstantPropertiesUtils.END_POIND;
        String accessKeyId = ConstantPropertiesUtils.ACCESS_KEY_ID;
        String accessKeySecret = ConstantPropertiesUtils.ACCESS_KEY_SECRET;
        String bucketName = ConstantPropertiesUtils.BUCKET_NAME;

        // 创建OSSClient实例。
        OSS ossClient = new OSSClientBuilder().build(endPoint, accessKeyId, accessKeySecret);

        // 删除文件。
        //https://guli-file-191125.oss-cn-beijing.aliyuncs.com/
        String host = "https://" + bucketName + "." + endPoint + "/";
        String objectName = url.substring(host.length());
        ossClient.deleteObject(bucketName, objectName);

        // 关闭OSSClient。
        ossClient.shutdown();
    }
}
