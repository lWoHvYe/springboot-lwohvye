package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.UserLog;

import java.util.List;

public interface UserLogService {


    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);

    List<UserLog> list(String username, String startDate, String endDate, int pages, int limits);

    int selectCount(String username, String startDate, String endDate);
}
