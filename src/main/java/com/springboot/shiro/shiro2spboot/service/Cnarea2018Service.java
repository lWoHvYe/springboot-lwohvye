package com.springboot.shiro.shiro2spboot.service;

import com.github.pagehelper.PageInfo;
import com.springboot.shiro.shiro2spboot.entity.Cnarea2018;

import java.util.concurrent.CompletableFuture;

public interface Cnarea2018Service{


    int deleteByPrimaryKey(Integer id);

    int insert(Cnarea2018 record);

    int insertSelective(Cnarea2018 record);

    Cnarea2018 selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(Cnarea2018 record);

    int updateByPrimaryKey(Cnarea2018 record);

    CompletableFuture<PageInfo<Cnarea2018>> list(String province, Integer finalLevel, int page, int pageSize);
}
