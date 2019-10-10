package com.springboot.shiro.shiro2spboot.config;

import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Configuration;

import java.lang.reflect.Method;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.config
 * @className RedisCachingConfigurer
 * @description 缓存配置类，改类用于配置部分缓存的属性CachingConfigurer
 * @date 2019/10/10 14:44
 */
@Configuration
public class RedisCachingConfigurer extends CachingConfigurerSupport {

    /**
     * @return org.springframework.cache.interceptor.KeyGenerator
     * @description 定制key的生成策略，使用类名 + 方法名 + 参数名共同组成 key
     * @params []
     * @author Hongyan Wang
     * @date 2019/10/10 14:55
     */
    @Override
    public KeyGenerator keyGenerator() {
        return (Object target, Method method, Object... params) -> {
            StringBuilder stringBuilder = new StringBuilder(16);
            stringBuilder.append(target.getClass().getName()).append("_")
                    .append(method.getName()).append("_");
            for (Object param : params) {
                stringBuilder.append(param).append(",");
            }
            return stringBuilder.substring(0, stringBuilder.length() - 1);
        };
    }
}
