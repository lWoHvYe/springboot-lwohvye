package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.repository.PermissionDao;
import com.springboot.shiro.shiro2spboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public List<Permission> findPermission(String name, Pageable pageable) {
        return permissionDao.findPermission(name,pageable);
    }

    @Override
    public void savePermission(Permission permission) {
        permissionDao.save(permission);
    }

    @Override
    public void deletePermission(Permission permission) {
        permissionDao.delete(permission);
    }
}
