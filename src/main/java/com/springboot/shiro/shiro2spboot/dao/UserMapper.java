package com.springboot.shiro.shiro2spboot.dao;
import org.apache.ibatis.annotations.Param;
import java.util.List;

import com.springboot.shiro.shiro2spboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface UserMapper {
    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);

    List<User> selectByAll(User user);


}