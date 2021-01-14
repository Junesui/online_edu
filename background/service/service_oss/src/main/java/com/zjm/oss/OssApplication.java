package com.zjm.oss;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.context.annotation.ComponentScan;

//数据源不进行自动配置  启动时不自动加载数据库信息  避免报错
@SpringBootApplication(exclude = DataSourceAutoConfiguration.class)
@EnableDiscoveryClient //nacos
@ComponentScan(basePackages = {"com.zjm"})
public class OssApplication {
    public static void main(String[] args) {
        SpringApplication.run(OssApplication.class,args);
    }
}
