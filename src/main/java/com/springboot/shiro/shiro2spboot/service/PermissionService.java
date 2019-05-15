package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.Permission;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface PermissionService {
    List<Permission> findPermission(String name, Pageable pageable);

    void savePermission(Permission permission);

    void deletePermission(Permission permission);
}
