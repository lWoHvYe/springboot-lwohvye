package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlaveRoleMapper {
    Role selectByPrimaryKey(Long id);

    /**
     * @return java.util.List<com.springboot.shiro.shiro2spboot.entity.Permission>
     * @description 根据角色Id获取对应的权限列表
     * @params [id]
     * @author Hongyan Wang
     * @date 2019/12/5 10:37
     */
    List<Permission> selectPermissionByRoleId(Long id);

}