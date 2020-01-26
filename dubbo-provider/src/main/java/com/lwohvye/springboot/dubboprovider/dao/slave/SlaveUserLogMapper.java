package com.lwohvye.springboot.dubboprovider.dao.slave;

import com.lwohvye.springboot.dubbointerface.entity.UserLog;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface SlaveUserLogMapper {
    UserLog selectByPrimaryKey(Integer id);

    List<UserLog> list(@Param("username") String username,
                       @Param("startDate") String startDate, @Param("endDate") String endDate);
}