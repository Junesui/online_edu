package com.june.servicebase;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.google.common.base.Predicates;


import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration//配置类
@EnableSwagger2 //swagger注解
public class SwaggerConfig {

	@Bean
    public Docket webApiConfig(){
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("webApi")
                .apiInfo(webApiInfo())
                .select()
                .paths(Predicates.not(PathSelectors.regex("/admin/.*"))) //包含路径时不显示
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .build();
        
    }

    private ApiInfo webApiInfo(){
        return new ApiInfoBuilder()
                .title("这是ApiInfo的title")
                .description("这是ApiInfo的description")
                .version("这是ApiInfo的version")
                .contact(new Contact("june", "http://june.com", "june@qq.com"))
                .build();
    }
	
}
