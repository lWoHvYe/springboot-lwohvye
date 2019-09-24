package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.repository.PermissionDao;
import com.springboot.shiro.shiro2spboot.service.PermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class PermissionServiceImpl implements PermissionService {
    @Autowired
    private PermissionDao permissionDao;

    @Override
    public void findPermission(String name, PageUtil<Permission> pageUtil) {
        Page<Permission> permissionPage = permissionDao.findPermission(name,pageUtil.obtPageable());
        pageUtil.setPageEntity(permissionPage);
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
