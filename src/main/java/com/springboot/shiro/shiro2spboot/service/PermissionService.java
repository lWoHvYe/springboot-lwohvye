package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Permission;

import java.util.List;

public interface PermissionService {
    void findPermission(String name, PageUtil<Permission> pageUtil);

    void savePermission(Permission permission);

    void deletePermission(Permission permission);

    int deleteByPrimaryKey(Long id);

    int insert(Permission record);

    int insertSelective(Permission record);

    Permission selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Permission record);

    int updateByPrimaryKey(Permission record);
}

