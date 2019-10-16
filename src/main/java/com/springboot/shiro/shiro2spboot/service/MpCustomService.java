package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;

/**
 *  Service接口
 *
 * @author author
 * @since 2019-10-08
 */
public interface MpCustomService{

    Object list();

    Object save(MpCustomEntity mpCustomEntity);

    void delete(int customId);

    Object searchById(int customId);

    Object update(MpCustomEntity mpCustomEntity);
}
