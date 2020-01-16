package com.springboot.shiro.shiro2spboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.shiro.shiro2spboot.dao.slave.SlaveCnarea2018Mapper;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import com.springboot.shiro.shiro2spboot.dao.master.MasterCnarea2018Mapper;
import com.springboot.shiro.shiro2spboot.entity.Cnarea2018;
import com.springboot.shiro.shiro2spboot.service.Cnarea2018Service;

import java.util.concurrent.CompletableFuture;

@Service
@Slf4j
public class Cnarea2018ServiceImpl implements Cnarea2018Service {

    @Autowired
    private MasterCnarea2018Mapper masterCnarea2018Mapper;
    @Autowired
    private SlaveCnarea2018Mapper slaveCnarea2018Mapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return masterCnarea2018Mapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Cnarea2018 record) {
        return masterCnarea2018Mapper.insert(record);
    }

    @Override
    public int insertSelective(Cnarea2018 record) {
        return masterCnarea2018Mapper.insertSelective(record);
    }

    @Override
    public Cnarea2018 selectByPrimaryKey(Integer id) {
        return slaveCnarea2018Mapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Cnarea2018 record) {
        return masterCnarea2018Mapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Cnarea2018 record) {
        return masterCnarea2018Mapper.updateByPrimaryKey(record);
    }

    //    标识异步方法
    @Async
    @Override
    public CompletableFuture<PageInfo<Cnarea2018>> list(String province, int page, int pageSize) {
        log.warn(Thread.currentThread().getName() + " start this task!");
        Cnarea2018 cnarea2018 = new Cnarea2018();
        cnarea2018.setMergerName(province);
        PageInfo<Cnarea2018> pageInfo = PageHelper.startPage(page, pageSize, "id asc").doSelectPageInfo(() -> slaveCnarea2018Mapper.selectByAll(cnarea2018));
//        暂停一会，使异步更明显
//        try {
//            Thread.sleep(10000L);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
        log.warn(Thread.currentThread().getName() + " finish this task!!");
        //返回一个已经用给定值完成的新的CompletableFuture。
        return CompletableFuture.completedFuture(pageInfo);

    }

}
