package com.springboot.shiro.shiro2spboot.dao.master;

import com.springboot.shiro.shiro2spboot.entity.Cnarea2018;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterCnarea2018Mapper {
    int deleteByPrimaryKey(Integer id);

    int insert(Cnarea2018 record);

    int insertSelective(Cnarea2018 record);

    int updateByPrimaryKeySelective(Cnarea2018 record);

    int updateByPrimaryKey(Cnarea2018 record);
}