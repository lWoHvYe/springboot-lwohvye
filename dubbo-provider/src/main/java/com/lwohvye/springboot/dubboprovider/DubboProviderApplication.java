package com.lwohvye.springboot.dubboprovider;

import org.apache.dubbo.config.spring.context.annotation.EnableDubbo;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.event.ApplicationEnvironmentPreparedEvent;
import org.springframework.cache.annotation.EnableCaching;
import org.springframework.context.ApplicationListener;
import org.springframework.core.env.Environment;

/**
 * @description
 * @author Hongyan Wang
 * @date 2020/1/26 22:01
 */
@SpringBootApplication
//开启dubbo相关自动配置
@EnableDubbo
//开启缓存
@EnableCaching
//指定实体类位置，解决启动报找不到实体类
@EntityScan(basePackages = {"com.lwohvye.springboot.dubbointerface.entity"})
public class DubboProviderApplication {

    public static void main(String[] args) {
        new SpringApplicationBuilder(DubboProviderApplication.class)
                .listeners((ApplicationListener<ApplicationEnvironmentPreparedEvent>) event -> {
                    Environment environment = event.getEnvironment();
                    int port = environment.getProperty("embedded.zookeeper.port", int.class);
                    new EmbeddedZooKeeper(port, false).start();
                })
                .run(args);
    }

}
