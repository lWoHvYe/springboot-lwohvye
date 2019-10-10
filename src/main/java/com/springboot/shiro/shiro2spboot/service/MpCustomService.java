package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;

import java.util.List;

/**
 *  Service接口
 *
 * @author author
 * @since 2019-10-08
 */
public interface MpCustomService{

    List<MpCustomEntity> list();

    MpCustomEntity save(MpCustomEntity mpCustomEntity);

    void delete(int customId);

    MpCustomEntity searchById(int customId);

    MpCustomEntity update(MpCustomEntity mpCustomEntity);
}
