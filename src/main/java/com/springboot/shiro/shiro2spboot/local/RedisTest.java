package com.springboot.shiro.shiro2spboot.local;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;
import java.util.concurrent.TimeUnit;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.local
 * @className RedisTest
 * @description
 * @date 2019/10/6 17:19
 */
@SpringBootTest
@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = {"classpath:application.properties"})
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;

    @Test
    public void redisTest() {
        String key = "keySet";
        redisTemplate.opsForValue().set(key, "keySet's Value");
        redisTemplate.expire(key, 100, TimeUnit.SECONDS);
        String value = (String) redisTemplate.opsForValue().get(key);
        System.out.println(value);
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 12; i++) {
                System.out.println(redisTemplate.getExpire(key));
                try {
                    Thread.sleep(10000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }
        });
//        Thread thread = new Thread(() -> {
//            for (int i = 0; i < 12; i++) {
//                redisTemplate.getExpire(key);
//                try {
//                    Thread.sleep(10000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
//        });
//        thread.start();
        CompletableFuture<Void> allOf = CompletableFuture.allOf(completableFuture);
        allOf.join();
    }
}
