package com.springboot.shiro.shiro2spboot.entity;

import com.alibaba.fastjson.annotation.JSONField;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.io.Serializable;

@Getter
@Setter
@Entity
@Table(name = "user")
public class User implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    @GeneratedValue
    private Long uid;
    @Column(unique = true)
    private String username;
    private String name;//昵称
    //    从json串中移除密码和盐
    @JSONField(serialize = false)
    private String password;
    @JSONField(serialize = false)
    private String salt;//加密密码的盐
    private byte state;//用户状态

    //    一个用户对应一个角色，一个角色对应多个用户，用户是Many端
    @ManyToOne(fetch = FetchType.EAGER)//配置单向立即加载
    @JoinColumn(name = "role_id")
    private Role roles;

    /**
     * 重新定义盐为用户名+salt，增加安全性
     *
     * @return
     */
    @JSONField(serialize = false)
    public String getCredentialsSalt() {
        return this.username + this.salt;
    }
}
