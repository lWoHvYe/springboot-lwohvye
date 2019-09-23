package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.repository.RoleDao;
import com.springboot.shiro.shiro2spboot.service.RoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class RoleServiceImpl implements RoleService {

    @Autowired
    private RoleDao roleDao;

    @Override
    public void findRole(String roleName, PageUtil<Role> pageUtil) {
        Page<Role> rolePage = roleDao.findRole(roleName, pageUtil.obtPageable());
        pageUtil.setPageEntity(rolePage);
    }

    @Override
    public void saveRole(Role role, String permissionId) {
        savePermissionId(role, permissionId);
        roleDao.save(role);
    }

    @Override
    public void deleteRole(Role role) {
        roleDao.delete(role);
    }


    /**
     * 设置权限，对于修改操作，jpa默认方法为相关表中先删再添加
     * @param role
     * @param permissionId
     */
    private void savePermissionId(Role role, String permissionId) {
        List<Permission> permissions = new ArrayList<>();
        if (permissionId.contains(",")) {
            String[] permissionIdArray = permissionId.split(",");
            for (String id : permissionIdArray) {
                Permission permission = new Permission();
                permission.setId(Long.parseLong(id));
                permissions.add(permission);
            }
        } else {
            Permission permission = new Permission();
            permission.setId(Long.parseLong(permissionId));
            permissions.add(permission);
        }
        role.setPermissions(permissions);
    }
}
