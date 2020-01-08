package com.springboot.shiro.shiro2spboot.service;

import com.github.pagehelper.PageInfo;
import com.springboot.shiro.shiro2spboot.entity.UserLog;

public interface UserLogService {


    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);

    PageInfo<UserLog> list(String username, String startDate, String endDate, int page, int pageSize);

    int selectCount(String username, String startDate, String endDate);
}
