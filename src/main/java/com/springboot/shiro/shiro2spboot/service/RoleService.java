package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Role;

public interface RoleService {
    void findRole(String roleName, PageUtil<Role> pageUtil);

    void saveRole(Role role, String permissionId);

    void deleteRole(Role role);
}
