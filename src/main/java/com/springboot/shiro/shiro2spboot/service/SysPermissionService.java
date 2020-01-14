package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Permission;

public interface SysPermissionService {
    void findPermission(String name, PageUtil<Permission> pageUtil);

    int savePermission(Permission permission);

    int deletePermission(Permission permission);

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}

