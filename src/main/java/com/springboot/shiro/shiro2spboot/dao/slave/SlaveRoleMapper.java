package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface SlaveRoleMapper {
    Role selectByPrimaryKey(Long id);


}