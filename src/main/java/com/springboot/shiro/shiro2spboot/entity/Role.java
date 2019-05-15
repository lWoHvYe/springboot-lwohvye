package com.springboot.shiro.shiro2spboot.entity;

import lombok.Data;
import lombok.Getter;
import lombok.Setter;
import org.springframework.context.annotation.Lazy;


import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class Role implements Serializable {

    @Id
    @GeneratedValue
    private Long id;
    private String role;
    private String description;//角色描述
    private Boolean available = Boolean.FALSE;//是否可用
    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(name = "RolePermission",joinColumns = {@JoinColumn(name = "roleId")},inverseJoinColumns = {@JoinColumn(name = "permissionId")})
    private List<Permission> permissions;
    @ManyToMany
    @JoinTable(name = "UserRole",joinColumns = {@JoinColumn(name = "roleId")},inverseJoinColumns = {@JoinColumn(name = "uid")})
    private List<User> users;
}
