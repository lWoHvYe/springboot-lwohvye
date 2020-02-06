package com.lwohvye.springboot.dubboconsumer.controller;

import com.lwohvye.springboot.dubboconsumer.common.util.BloomFilterHelper;
import com.lwohvye.springboot.dubboconsumer.common.util.RedisUtil;
import com.lwohvye.springboot.dubbointerface.service.Cnarea2018Service;
import org.apache.dubbo.config.annotation.Reference;
import org.apache.shiro.authz.annotation.RequiresRoles;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springboot.dubboconsumer.controller
 * @className BloomFilterController
 * @description
 * @date 2020/2/6 15:50
 */
@RestController
@RequestMapping("/bloomFilter")
//限定该类只有admin角色可以访问
@RequiresRoles("admin")
public class BloomFilterController {

    @Reference(version = "${lwohvye.service.version}")
    private Cnarea2018Service cnarea2018Service;

    @Autowired
    private RedisUtil redisUtil;
    @Autowired
    private BloomFilterHelper<String> bloomFilterHelper;

    /**
     * @description 将行政区划中的省名放入
     * @params []
     * @return java.lang.String
     * @author Hongyan Wang
     * @date 2020/2/6 17:50
     */
    @GetMapping("/addCnareaPro")
    public String addCnareaPro() {
        cnarea2018Service.listProName().forEach(pro -> redisUtil.addByBloomFilter(bloomFilterHelper, "cnareaPro", pro));
        return "success";
    }
}
