package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlavePermissionMapper {
    Permission selectByPrimaryKey(Long id);
}