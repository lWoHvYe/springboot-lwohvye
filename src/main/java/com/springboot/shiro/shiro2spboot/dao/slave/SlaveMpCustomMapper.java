package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.data.repository.query.Param;

import java.util.List;

@Mapper
public interface SlaveMpCustomMapper {

    MpCustomEntity selectByPrimaryKey(Integer customId);

    List<MpCustomEntity> list();

    MpCustomEntity searchById(@Param("customId") int customId);
}