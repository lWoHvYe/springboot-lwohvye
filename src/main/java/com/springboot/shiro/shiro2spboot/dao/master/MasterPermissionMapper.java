package com.springboot.shiro.shiro2spboot.dao.master;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterPermissionMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}