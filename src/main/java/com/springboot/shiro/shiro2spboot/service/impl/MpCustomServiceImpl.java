package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.dao.MpCustomMapper;
import com.springboot.shiro.shiro2spboot.entity.MpCustomEntity;
import com.springboot.shiro.shiro2spboot.local.redis.RedisKeys;
import com.springboot.shiro.shiro2spboot.service.MpCustomService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.*;
import org.springframework.stereotype.Service;

/**
 * @author Hongyan Wang
 * @packageName com.springboot.shiro.shiro2spboot.service.impl
 * @interfaceName MpCustomServiceImpl
 * @description 使用redis缓存，分别缓存客户列表及单个客户两种数据，
 * 执行添加操作时，清除客户列表缓存
 * 执行修改、删除操作时，清除客户列表和对应客户缓存
 * @date 2019/10/10 13:37
 */
@Service
//CacheConfig注解用来配置缓存中一些公共属性的值
@CacheConfig(cacheNames = "mpCustom")
public class MpCustomServiceImpl implements MpCustomService {

    @Autowired
    private MpCustomMapper mpCustomMapper;

    /**
     * @return java.util.List<com.springboot.shiro.shiro2spboot.entity.MpCustomEntity>
     * @description 获取企业列表，
     * @params []
     * @author Hongyan Wang
     * @date 2019/10/10 16:09
     */
    @Cacheable(key = "'mpCustomList'")
    @Override
    public Object list() {
        return mpCustomMapper.list();
    }

    /**
     * @return com.springboot.shiro.shiro2spboot.entity.MpCustomEntity
     * @description 添加客户，清空客户列表缓存并将新加客户加入缓存
     * @params [mpCustomEntity]
     * @author Hongyan Wang
     * @date 2019/10/10 17:10
     */
    @Caching(evict = {@CacheEvict(key = "'mpCustomList'", beforeInvocation = true)},
            put = {@CachePut(key = "'com.springboot.shiro.shiro2spboot.service.impl.MpCustomServiceImpl_searchById_'+#mpCustomEntity.customId",
                    cacheNames = "mpCustom::" + RedisKeys.REDIS_EXPIRE_TIME_KEY + "=600")}
    )
    @Override
    public Object save(MpCustomEntity mpCustomEntity) {
        mpCustomMapper.save(mpCustomEntity);
        return mpCustomEntity;
    }

    /**
     * @return void
     * @description 根据id删除企业，并清除对应的缓存，对于需要对多个key执行操作，使用@Caching注解
     * 设置beforeInvocation = true，是防止方法执行抛异常导致缓存未被清
     * @params [customId]
     * @author Hongyan Wang
     * @date 2019/10/10 14:28
     */
    @Caching(evict = {
            @CacheEvict(key = "'mpCustomList'", beforeInvocation = true),
            @CacheEvict(key = "'com.springboot.shiro.shiro2spboot.service.impl.MpCustomServiceImpl_searchById_'+#customId",
                    cacheNames = "mpCustom::" + RedisKeys.REDIS_EXPIRE_TIME_KEY + "=600", beforeInvocation = true)
    })
    @Override
    public void delete(int customId) {
        mpCustomMapper.delete(customId);
    }

    /**
     * @return com.springboot.shiro.shiro2spboot.entity.MpCustomEntity
     * @description 根据id查询客户信息，使用缓存，key为默认生成策略生成 完整类名 + 方法名 + 参数值
     * 因为空数据不应放入缓存，故使用unless属性进行排除，当判断为true时，不缓存
     * @params [customId]
     * @author Hongyan Wang
     * @date 2019/10/10 16:31
     */
    @Cacheable(unless = "#result == null", cacheNames = "mpCustom::" + RedisKeys.REDIS_EXPIRE_TIME_KEY + "=600")
    @Override
    public Object searchById(int customId) {
        return mpCustomMapper.searchById(customId);
    }


    /**
     * @return com.springboot.shiro.shiro2spboot.entity.MpCustomEntity
     * @description 修改客户信息，需要清除客户列表及对应客户的缓存，并重新添加对应客户的缓存，所以需方法返回修改后的客户
     * @params [mpCustomEntity]
     * @author Hongyan Wang
     * @date 2019/10/10 17:13
     */
    @Caching(evict = {
            @CacheEvict(key = "'mpCustomList'", beforeInvocation = true),
            @CacheEvict(key = "'com.springboot.shiro.shiro2spboot.service.impl.MpCustomServiceImpl_searchById_'+#mpCustomEntity.customId",
                    beforeInvocation = true, cacheNames = "mpCustom::" + RedisKeys.REDIS_EXPIRE_TIME_KEY + "=600")},
            put = {@CachePut(key = "'com.springboot.shiro.shiro2spboot.service.impl.MpCustomServiceImpl_searchById_'+#mpCustomEntity.customId",
                    cacheNames = "mpCustom::" + RedisKeys.REDIS_EXPIRE_TIME_KEY + "=600")}
    )
    @Override
    public Object update(MpCustomEntity mpCustomEntity) {
        mpCustomMapper.update(mpCustomEntity);
        return mpCustomMapper.searchById(mpCustomEntity.getCustomId());
    }
}
