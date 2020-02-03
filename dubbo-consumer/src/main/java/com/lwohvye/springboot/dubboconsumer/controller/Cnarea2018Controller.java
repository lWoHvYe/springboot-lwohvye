package com.lwohvye.springboot.dubboconsumer.controller;

import com.github.pagehelper.PageInfo;
import com.lwohvye.springboot.dubbointerface.entity.Cnarea2018;
import com.lwohvye.springboot.dubbointerface.service.Cnarea2018Service;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import org.apache.dubbo.config.annotation.Reference;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Arrays;
import java.util.List;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;

/**
 * @author Hongyan Wang
 * @packageName com.lwohvye.springboot.dubboconsumer.controller
 * @className Cnarea2018Controller
 * @description
 * @date 2020/1/16 15:12
 */
@RequestMapping("/cnarea")
@RestController
public class Cnarea2018Controller {

    @Reference(version = "${lwohvye.service.version}")
    private Cnarea2018Service cnarea2018Service;

    /**
     * @return com.lwohvye.springboot.dubboconsumer.common.util.ResultModel<com.github.pagehelper.PageInfo < com.lwohvye.springboot.dubbo.entity.Cnarea2018>>
     * @description 异步测试方法，province为使用逗号分隔的两位的省级区划名
     * @params [province, page, pageSize]
     * @author Hongyan Wang
     * @date 2020/1/16 22:13
     */
    @ApiOperation(value = "根据省名获取下属区划", notes = "多个省名使用逗号分隔")
    @ApiImplicitParam(name = "levels", value = "查询区划级别，实际可使用下拉选择 0省、直辖市 1市 2区县 3街道办事处 4社区居委会")
    @PostMapping("/list")
    public List<PageInfo<Cnarea2018>> list(String province, String levels, int page, int pageSize) {
//        设置区划级别，0省、直辖市 1市 2区县 3街道办事处 4社区居委会
        Integer level = null;
        if (!StringUtils.isEmpty(levels)) {
            level = Integer.parseInt(levels);
        }
//        设置参数值
        Integer finalLevel = level;
//      切割字符串
        var completableFutureList = Arrays.stream(province.split(","))
//                用map后获取一个新的流，可以继续操作，用foreach后流便没了
                .map(name -> cnarea2018Service.list(name, finalLevel, page, pageSize))
                .collect(Collectors.toList());
        return completableFutureList.stream()
//                将任务放入队列，等待结束
                .map(CompletableFuture::join)
                .collect(Collectors.toList());
    }

}
