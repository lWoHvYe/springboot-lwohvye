package com.springboot.shiro.shiro2spboot.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import com.springboot.shiro.shiro2spboot.common.redis.RedisCacheWriterCustomer;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.CacheManager;
import org.springframework.cache.annotation.CachingConfigurerSupport;
import org.springframework.cache.interceptor.KeyGenerator;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.cache.RedisCacheConfiguration;
import org.springframework.data.redis.cache.RedisCacheManager;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.serializer.RedisSerializationContext;

import java.lang.reflect.Method;
import java.time.Duration;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.config
 * @className RedisCachingConfigurer
 * @description 缓存配置类，改类用于配置部分缓存的属性CachingConfigurer，这里使用注解的方式使用缓存
 * @date 2019/10/10 14:44
 */
@Configuration
public class RedisCachingConfigurer extends CachingConfigurerSupport {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    /**
     * @return org.springframework.cache.CacheManager
     * @description 重新cacheManager方法，配置相关属性
     * @params []
     * @author Hongyan Wang
     * @date 2019/10/15 16:13
     */
    @Bean
    @Override
    public CacheManager cacheManager() {
        var connectionFactory = redisTemplate.getConnectionFactory();
        var cacheWriterCustomer = new RedisCacheWriterCustomer(connectionFactory);
        return new RedisCacheManager(cacheWriterCustomer, redisCacheConfiguration());
    }

    @Bean
    public RedisCacheConfiguration redisCacheConfiguration() {
        RedisCacheConfiguration configuration = RedisCacheConfiguration.defaultCacheConfig();

        configuration = configuration.serializeValuesWith
//                使用FastJson的相关方法来序列化redis，需注意拿到的是JsonObject对象，所以添加注解的方法，返回值应为Object类型
        (RedisSerializationContext.SerializationPair.fromSerializer(new FastJsonRedisSerializer<>(Object.class)))
//                设置默认过期时间，600s
                .entryTtl(Duration.ofSeconds(600));

        return configuration;
    }

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
