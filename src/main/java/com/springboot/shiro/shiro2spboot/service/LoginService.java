package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.entity.User;

public interface LoginService {
    User findByUsername(String name);
}
