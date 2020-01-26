package com.lwohvye.springboot.dubboprovider.dao.slave;

import com.lwohvye.springboot.dubbointerface.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlaveRoleMapper {
    Role selectByPrimaryKey(Long id);


}