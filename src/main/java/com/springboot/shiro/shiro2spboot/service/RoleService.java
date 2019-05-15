package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.Role;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface RoleService {
    List<Role> findRole(String role, Pageable pageable);

    void saveRole(Role role, String permissionId);

    void deleteRole(Role role);
}
