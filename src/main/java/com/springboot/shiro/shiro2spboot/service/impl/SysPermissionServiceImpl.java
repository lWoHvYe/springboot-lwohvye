package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.datasource.DatabaseType;
import com.springboot.shiro.shiro2spboot.common.datasource.dataSource;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.dao.master.PermissionMapper;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.repository.PermissionDao;
import com.springboot.shiro.shiro2spboot.service.SysPermissionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

//权限相关只使用主库
@dataSource(DatabaseType.MASTER)
@Service
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private PermissionMapper permissionMapper;

    @Override
    public void findPermission(String name, PageUtil<Permission> pageUtil) {
        Page<Permission> permissionPage = permissionDao.findPermission(name, pageUtil.obtPageable());
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

    @Override
    public int deleteByPrimaryKey(Long id) {
        return permissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Permission record) {
        return permissionMapper.insert(record);
    }

    @Override
    public int insertSelective(Permission record) {
        return permissionMapper.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Long id) {
        return permissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return permissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return permissionMapper.updateByPrimaryKey(record);
    }
}

