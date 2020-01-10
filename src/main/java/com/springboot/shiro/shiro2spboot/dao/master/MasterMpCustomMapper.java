package com.springboot.shiro.shiro2spboot.dao.master;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

@Mapper
public interface MasterMpCustomMapper {
    int deleteByPrimaryKey(Integer customId);

    int insert(MpCustomEntity record);

    int insertSelective(MpCustomEntity record);

    int updateByPrimaryKeySelective(MpCustomEntity record);

    int updateByPrimaryKey(MpCustomEntity record);

    void save(MpCustomEntity mpCustomEntity);

    void delete(@Param("customId") int customId);

    void update(MpCustomEntity mpCustomEntity);
}