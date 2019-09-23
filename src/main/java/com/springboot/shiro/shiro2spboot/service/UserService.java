package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.User;

public interface UserService {
    User findByUsername(String name);

    PageUtil<User> findUser(String username, PageUtil<User> pageable);

    void saveUser(User user, String roleId);

    void deleteUser(User user);
}
