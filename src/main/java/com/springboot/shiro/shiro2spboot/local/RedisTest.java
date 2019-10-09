package com.springboot.shiro.shiro2spboot.local;

import com.alibaba.fastjson.JSON;
import com.springboot.shiro.shiro2spboot.common.util.RedisUtil;
import com.springboot.shiro.shiro2spboot.entity.User;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.concurrent.CompletableFuture;

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
        RedisUtil redisUtil = new RedisUtil(redisTemplate);
        String key = "keySet";
//        创建实体类并赋值
        User user = new User();
        user.setUid(10086L);
        user.setName("redis存储用户");
        user.setUsername("redisUser");
        user.setPassword("redis");
        user.setSalt("加密用Salt");
//        将实体类放入redis中
        redisUtil.set(key, user);
//        设置过期时间
        redisUtil.expire(key, 100);
//        从redis中取出实体类对象Object类型
        var objUser = redisUtil.get(key);
//        转换为json串，只能将Object类型转为json串
        var strUser = JSON.toJSONString(objUser);
//        将json串转换为User对象
        var tranUser = JSON.parseObject(strUser,User.class);
        System.out.println(tranUser.toString());
        CompletableFuture completableFuture = CompletableFuture.runAsync(() -> {
            for (int i = 0; i < 12; i++) {
                var expires = redisUtil.getExpire(key);
                if (expires <= 0)
                    break;
                System.out.println(expires);
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
