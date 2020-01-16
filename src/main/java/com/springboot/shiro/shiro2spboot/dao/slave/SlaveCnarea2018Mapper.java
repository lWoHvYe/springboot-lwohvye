package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.Cnarea2018;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlaveCnarea2018Mapper {
    Cnarea2018 selectByPrimaryKey(Integer id);
    List<Cnarea2018> selectByAll(Cnarea2018 cnarea2018);

}