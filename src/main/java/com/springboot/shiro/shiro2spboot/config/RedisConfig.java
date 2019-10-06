package com.springboot.shiro.shiro2spboot.config;

import com.alibaba.fastjson.support.spring.FastJsonRedisSerializer;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.data.redis.serializer.StringRedisSerializer;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.config
 * @className RedisConfig
 * @description redis配置类
 * @date 2019/10/6 16:32
 */

@Configuration
public class RedisConfig {

    @Bean
    @ConditionalOnMissingBean(name = "redisTemplate")
    public RedisTemplate<String, Object> redisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
//        使用FastJsonRedisSerializer来序列化和反序列化redis的value值
        var fastJsonRedisSerializer = new FastJsonRedisSerializer<Object>(Object.class);
//        使用StringRedisSerializer来序列化和反序列化redis的key值
        var stringRedisSerializer = new StringRedisSerializer();
//        亦可使用Jackson2JsonRedisSerializer来序列化和反序列化redis的value值
//        var fastJsonRedisSerializer = new Jackson2JsonRedisSerializer<Object>(Object.class);
//        var om = new ObjectMapper();
//        om.setVisibility(PropertyAccessor.ALL, JsonAutoDetect.Visibility.ANY);
//        om.enableDefaultTyping(ObjectMapper.DefaultTyping.NON_FINAL);
//        fastJsonRedisSerializer.setObjectMapper(om);

        var template = new RedisTemplate<String, Object>();
        template.setConnectionFactory(redisConnectionFactory);
        template.setKeySerializer(stringRedisSerializer);
        template.setValueSerializer(fastJsonRedisSerializer);
        template.setHashKeySerializer(stringRedisSerializer);
        template.setHashValueSerializer(fastJsonRedisSerializer);
        template.afterPropertiesSet();
        return template;
    }

    @Bean
    @ConditionalOnMissingBean(StringRedisTemplate.class)
    public StringRedisTemplate stringRedisTemplate(
            RedisConnectionFactory redisConnectionFactory) {
        var template = new StringRedisTemplate();
        template.setConnectionFactory(redisConnectionFactory);
        return template;
    }
}
