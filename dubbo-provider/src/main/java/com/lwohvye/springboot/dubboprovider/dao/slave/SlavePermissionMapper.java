package com.lwohvye.springboot.dubboprovider.dao.slave;

import com.lwohvye.springboot.dubbointerface.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlavePermissionMapper {
    Permission selectByPrimaryKey(Long id);
}