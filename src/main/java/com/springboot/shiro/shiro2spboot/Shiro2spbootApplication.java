package com.springboot.shiro.shiro2spboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@SpringBootApplication
//开启缓存
@EnableCaching
//开启SwaggerAPI
@EnableSwagger2
public class Shiro2spbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shiro2spbootApplication.class, args);
    }

}
