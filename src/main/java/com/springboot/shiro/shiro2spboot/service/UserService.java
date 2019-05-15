package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.User;
import org.springframework.data.domain.Pageable;

import java.util.List;

public interface UserService {
    User findByUsername(String name);

    List<User> findUser(String username, Pageable pageable);

    void saveUser(User user, String roleId);

    void deleteUser(User user);
}
