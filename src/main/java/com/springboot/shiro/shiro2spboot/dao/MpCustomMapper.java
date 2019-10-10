package com.springboot.shiro.shiro2spboot.dao;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;
import org.springframework.data.repository.query.Param;

import java.util.List;

/**
 * Mapper 接口
 *
 * @author author
 * @since 2019-10-08
 */
public interface MpCustomMapper {

    List<MpCustomEntity> list();

    void save(MpCustomEntity mpCustomEntity);

    void delete(@Param("customId") int customId);

    MpCustomEntity searchById(@Param("customId") int customId);

    void update(MpCustomEntity mpCustomEntity);
}
