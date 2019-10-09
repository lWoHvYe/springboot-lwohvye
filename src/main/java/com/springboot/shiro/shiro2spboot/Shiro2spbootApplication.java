package com.springboot.shiro.shiro2spboot;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//开启缓存
@EnableCaching
//开启SwaggerAPI
@EnableSwagger2
//添加mapper bean扫描
//@MapperScan(value = "com.springboot.shiro.shiro2spboot.dao")
public class Shiro2spbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shiro2spbootApplication.class, args);
    }

}
