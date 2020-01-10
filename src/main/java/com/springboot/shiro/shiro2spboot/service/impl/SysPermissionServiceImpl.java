package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.datasource.DatabaseType;
import com.springboot.shiro.shiro2spboot.common.datasource.dataSource;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.dao.master.MasterPermissionMapper;
import com.springboot.shiro.shiro2spboot.dao.slave.SlavePermissionMapper;
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
    private MasterPermissionMapper masterPermissionMapper;
    @Autowired
    private SlavePermissionMapper slavePermissionMapper;

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
        return masterPermissionMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Permission record) {
        return masterPermissionMapper.insert(record);
    }

    @Override
    public int insertSelective(Permission record) {
        return masterPermissionMapper.insertSelective(record);
    }

    @Override
    public Permission selectByPrimaryKey(Long id) {
        return slavePermissionMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Permission record) {
        return masterPermissionMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Permission record) {
        return masterPermissionMapper.updateByPrimaryKey(record);
    }
}

