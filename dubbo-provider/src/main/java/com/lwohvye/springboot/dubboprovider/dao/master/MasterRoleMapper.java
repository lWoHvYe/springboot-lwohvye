package com.lwohvye.springboot.dubboprovider.dao.master;

import com.lwohvye.springboot.dubbointerface.entity.Role;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterRoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

}