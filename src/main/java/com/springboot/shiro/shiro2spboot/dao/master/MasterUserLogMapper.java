package com.springboot.shiro.shiro2spboot.dao.master;

import com.springboot.shiro.shiro2spboot.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterUserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);
}