package com.lwohvye.springboot.dubboprovider.dao.master;

import com.lwohvye.springboot.dubbointerface.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterUserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

}