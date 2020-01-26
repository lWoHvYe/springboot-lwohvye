package com.lwohvye.springboot.dubboprovider.dao.master;

import com.lwohvye.springboot.dubbointerface.entity.MpCustomEntity;
import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface MasterMpCustomMapper {
    int deleteByPrimaryKey(Integer customId);

    int insert(MpCustomEntity record);

    int insertSelective(MpCustomEntity record);

    int updateByPrimaryKeySelective(MpCustomEntity record);

    int updateByPrimaryKey(MpCustomEntity record);

}