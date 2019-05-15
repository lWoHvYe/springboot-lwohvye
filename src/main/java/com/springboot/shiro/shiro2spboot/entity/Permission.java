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
public class Permission implements Serializable {
    @Id
    @GeneratedValue
    private Long id;
    private String name;
    @Column(columnDefinition = "enum('menu','button')")
    private String resourceType;//资源类型,[menu|button]
    private String url;//资源路径
    private String permission;//权限字符串
    private Long parentId;//父编号
    private String parentIds;//父编号列表
    private Boolean available = Boolean.FALSE;//是否可用
    @ManyToMany
    @JoinTable(name = "RolePermission",joinColumns = {@JoinColumn(name = "permissionId")},inverseJoinColumns = {@JoinColumn(name = "roleId")})
    private List<Role> roles;
}
