package com.springboot.shiro.shiro2spboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;

@SpringBootApplication
@EnableCaching
public class Shiro2spbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shiro2spbootApplication.class, args);
    }

}
