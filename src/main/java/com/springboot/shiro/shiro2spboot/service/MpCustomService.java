package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;

/**
 * Service接口
 *
 * @author author
 * @since 2019-10-08
 */
public interface MpCustomService {

    Object list();

    Object save(MpCustomEntity mpCustomEntity);

    void delete(int customId);

    Object searchById(int customId);

    Object update(MpCustomEntity mpCustomEntity);

    int deleteByPrimaryKey(Integer customId);

    int insert(MpCustomEntity record);

    int insertSelective(MpCustomEntity record);

    MpCustomEntity selectByPrimaryKey(Integer customId);

    int updateByPrimaryKeySelective(MpCustomEntity record);

    int updateByPrimaryKey(MpCustomEntity record);
}

