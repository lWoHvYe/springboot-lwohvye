package com.springboot.shiro.shiro2spboot.dao;

import com.springboot.shiro.shiro2spboot.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.session.RowBounds;

import java.util.List;

@Mapper
public interface UserLogMapper {
    int deleteByPrimaryKey(Integer id);

    int insert(UserLog record);

    int insertSelective(UserLog record);

    UserLog selectByPrimaryKey(Integer id);

    int updateByPrimaryKeySelective(UserLog record);

    int updateByPrimaryKey(UserLog record);

    List<UserLog> list(@Param("username") String username,
                       @Param("startDate") String startDate, @Param("endDate") String endDate);

    int selectCount(@Param("username") String username, @Param("startDate") String startDate, @Param("endDate") String endDate);

}