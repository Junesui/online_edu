package com.june.eduservice.config;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.context.annotation.Configuration;

@Configuration
@MapperScan("com.june.eduservice.mapper")
public class EduConfig {

}
