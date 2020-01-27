package com.lwohvye.springboot.dubboprovider.serviceimpl;

import com.lwohvye.springboot.dubbointerface.common.util.PageUtil;
import com.lwohvye.springboot.dubbointerface.entity.Permission;
import com.lwohvye.springboot.dubbointerface.service.SysPermissionService;
import com.lwohvye.springboot.dubboprovider.dao.master.MasterPermissionMapper;
import com.lwohvye.springboot.dubboprovider.dao.slave.SlavePermissionMapper;
import com.lwohvye.springboot.dubboprovider.repository.PermissionDao;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;

//这里的@Service注解使用dubbo的，需注意
@Service(version = "${lwohvye.service.version}")
@Component
public class SysPermissionServiceImpl implements SysPermissionService {
    @Autowired
    private PermissionDao permissionDao;
    @Autowired
    private MasterPermissionMapper masterPermissionMapper;
    @Autowired
    private SlavePermissionMapper slavePermissionMapper;

    @Override
    public PageUtil<Permission> findPermission(String name, PageUtil<Permission> pageUtil) {
        Page<Permission> permissionPage = permissionDao.findPermission(name, pageUtil.obtPageable());
        pageUtil.setPageEntity(permissionPage);
        return pageUtil;
    }

    @Override
    public int savePermission(Permission permission) {
        permissionDao.save(permission);
        return 1;
    }

    @Override
    public int deletePermission(Permission permission) {
        permissionDao.delete(permission);
        return 1;
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

