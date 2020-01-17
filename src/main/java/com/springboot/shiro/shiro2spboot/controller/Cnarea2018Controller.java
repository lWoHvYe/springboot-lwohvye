package com.springboot.shiro.shiro2spboot.controller;

import com.github.pagehelper.PageInfo;
import com.springboot.shiro.shiro2spboot.entity.Cnarea2018;
import com.springboot.shiro.shiro2spboot.service.Cnarea2018Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.controller
 * @className Cnarea2018Controller
 * @description
 * @date 2020/1/16 15:12
 */
@RequestMapping("/cnarea")
@RestController
public class Cnarea2018Controller {

    @Autowired
    private Cnarea2018Service cnarea2018Service;

    /**
     * @return com.springboot.shiro.shiro2spboot.common.util.ResultModel<com.github.pagehelper.PageInfo < com.springboot.shiro.shiro2spboot.entity.Cnarea2018>>
     * @description 异步测试方法，province为使用逗号分隔的两位的省级区划名
     * @params [province, page, pageSize]
     * @author Hongyan Wang
     * @date 2020/1/16 22:13
     */
    @PostMapping("/list")
    public List<PageInfo<Cnarea2018>> list(String province, int page, int pageSize) {
//      切割字符串
        var completableFutureList = Arrays.stream(province.split(","))
//                用map后获取一个新的流，可以继续操作，用foreach后流便没了
                .map(name -> cnarea2018Service.list(name, page, pageSize))
                .collect(Collectors.toList());

        return completableFutureList.stream()
//                将任务放入队列，等待结束
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}
