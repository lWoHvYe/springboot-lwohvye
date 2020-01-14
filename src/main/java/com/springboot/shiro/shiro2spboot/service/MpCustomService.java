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

    int deleteByPrimaryKey(Integer customId);

    int insert(MpCustomEntity record);

    Object insertSelective(MpCustomEntity record);

    Object selectByPrimaryKey(Integer customId);

    Object updateByPrimaryKeySelective(MpCustomEntity record);

    int updateByPrimaryKey(MpCustomEntity record);
}

