package com.springboot.shiro.shiro2spboot.dao.master;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import com.springboot.shiro.shiro2spboot.entity.Role;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface RoleMapper {
    int deleteByPrimaryKey(Long id);

    int insert(Role record);

    int insertSelective(Role record);

    Role selectByPrimaryKey(Long id);

    int updateByPrimaryKeySelective(Role record);

    int updateByPrimaryKey(Role record);

    /**
     * @description 根据角色Id获取对应的权限列表
     * @params [id]
     * @return java.util.List<com.springboot.shiro.shiro2spboot.entity.Permission>
     * @author Hongyan Wang
     * @date 2019/12/5 10:37
     */
    List<Permission> selectPermissionByRoleId(Long id);

}