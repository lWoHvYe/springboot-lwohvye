package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.datasource.DatabaseType;
import com.springboot.shiro.shiro2spboot.common.datasource.dataSource;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.dao.master.MasterRoleMapper;
import com.springboot.shiro.shiro2spboot.dao.slave.SlaveRoleMapper;
import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.repository.RoleDao;
import com.springboot.shiro.shiro2spboot.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

//权限相关，只是用主库
@dataSource(DatabaseType.MASTER)
@Service
public class SysRoleServiceImpl implements SysRoleService {

    @Autowired
    private RoleDao roleDao;
    @Autowired
    private MasterRoleMapper masterRoleMapper;
    @Autowired
    private SlaveRoleMapper slaveRoleMapper;

    @Override
    public PageUtil<Role> findRole(String roleName, PageUtil<Role> pageUtil) {
        Page<Role> rolePage = roleDao.findRole(roleName, pageUtil.obtPageable());
        pageUtil.setPageEntity(rolePage);
        return pageUtil;
    }

    @Override
    public int saveRole(Role role, String permissionId) {
        savePermissionId(role, permissionId);
        roleDao.save(role);
        return 1;
    }

    @Override
    public int deleteRole(Role role) {
        roleDao.delete(role);
        return 1;
    }


    /**
     * 设置权限，对于修改操作，jpa默认方法为相关表中先删再添加
     *
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

    @Override
    public int deleteByPrimaryKey(Long id) {
        return masterRoleMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(Role record) {
        return masterRoleMapper.insert(record);
    }

    @Override
    public int insertSelective(Role record) {
        return masterRoleMapper.insertSelective(record);
    }

    @Override
    public Role selectByPrimaryKey(Long id) {
        return slaveRoleMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(Role record) {
        return masterRoleMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(Role record) {
        return masterRoleMapper.updateByPrimaryKey(record);
    }
}

