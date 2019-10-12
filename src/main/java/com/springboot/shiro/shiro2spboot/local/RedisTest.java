package com.springboot.shiro.shiro2spboot.local;

import com.alibaba.fastjson.JSON;
import com.springboot.shiro.shiro2spboot.common.util.DateTimeUtil;
import com.springboot.shiro.shiro2spboot.common.util.RedisUtil;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.MpCustomService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.MessageFormat;
import java.util.Objects;
import java.util.concurrent.CompletableFuture;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.local
 * @className RedisTest
 * @description
 * @date 2019/10/6 17:19
 */
//@SpringBootTest
//@RunWith(SpringRunner.class)
//@ContextConfiguration(locations = {"classpath:application.properties"})
@RestController
@RequestMapping("/redis")
@Slf4j
public class RedisTest {

    @Autowired
    private RedisTemplate<String, Object> redisTemplate;
    @Autowired
    private MpCustomService mpCustomService;

    public void redisTest() {
        var redisUtil = new RedisUtil(redisTemplate);
        var key = "keySet";
//        创建实体类并赋值
        var user = new User();
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
        var tranUser = JSON.parseObject(strUser, User.class);
        System.out.println(tranUser.toString());
        var completableFuture = CompletableFuture.runAsync(() -> {
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
        var allOf = CompletableFuture.allOf(completableFuture);
        allOf.join();
    }

    @GetMapping("/getKeys")
    public String getKeys() {
//        mpCustomService.searchById(8);
        Objects.requireNonNull(redisTemplate.keys("*")).forEach(e -> log.warn(MessageFormat.format("{0} | {1}", DateTimeUtil.getCurFormatTime(), e)));
//        mpCustomService.delete(8);
        return "success";
    }
}
