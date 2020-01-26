package com.lwohvye.springboot.dubbointerface.service;

import com.lwohvye.springboot.dubbointerface.common.util.PageUtil;
import com.lwohvye.springboot.dubbointerface.entity.Role;

public interface SysRoleService {
    PageUtil<Role> findRole(String roleName, PageUtil<Role> pageUtil);

    int saveRole(Role role, String permissionId);

    int deleteRole(Role role);

    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);
}

