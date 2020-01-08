package com.springboot.shiro.shiro2spboot;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cache.annotation.EnableCaching;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

/**
 * @author Hongyan Wang
 * @description Springboot项目入口，在此处开启了缓存@EnableCaching、开启了SwaggerAPI @EnableSwagger2
 * 如果使用mybatis，还需要在此配置mapper扫描，@MapperScan(value="dao路径")，
 * 因为该项目配置了数据源切换，所以将此配置放在相关类上
 * @date 2019/10/10 13:50
 * @see com.springboot.shiro.shiro2spboot.common.datasource.DruidMonitorConfig
 */
@SpringBootApplication
//开启缓存
@EnableCaching
//开启SwaggerAPI
@EnableSwagger2
//添加mapper bean扫描，该配置移到数据源切换类上
//@MapperScan(value = "com.springboot.shiro.shiro2spboot.dao")
public class Shiro2spbootApplication {

    public static void main(String[] args) {
        SpringApplication.run(Shiro2spbootApplication.class, args);
    }

}
