package com.springboot.shiro.shiro2spboot.dao;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface MpCustomMapper {
    int deleteByPrimaryKey(Integer customId);

    int insert(MpCustomEntity record);

    int insertSelective(MpCustomEntity record);

    MpCustomEntity selectByPrimaryKey(Integer customId);

    int updateByPrimaryKeySelective(MpCustomEntity record);

    int updateByPrimaryKey(MpCustomEntity record);

    List<MpCustomEntity> list();

    void save(MpCustomEntity mpCustomEntity);

    void delete(@Param("customId") int customId);

    MpCustomEntity searchById(@Param("customId") int customId);

    void update(MpCustomEntity mpCustomEntity);
}