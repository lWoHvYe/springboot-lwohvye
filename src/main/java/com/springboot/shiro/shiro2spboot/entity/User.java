package com.springboot.shiro.shiro2spboot.entity;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Entity
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long uid;
    @Column(unique = true)
    private String username;
    private String name;//昵称
    private String password;
    private String salt;//加密密码的盐
    private byte state;//用户状态
    @ManyToMany(fetch = FetchType.EAGER)//立即从数据库中进行数据加载
    @JoinTable(name = "UserRole", joinColumns = {@JoinColumn(name = "uid")},
            inverseJoinColumns = {@JoinColumn(name = "roleId")})//设置关联表、关联字段(主键)及副表的关联字段(主键)
    private List<Role> roles;

    /**
     * 重新定义盐为用户名+salt，增加安全性
     *
     * @return
     */
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }
}
