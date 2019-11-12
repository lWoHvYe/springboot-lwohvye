package com.springboot.shiro.shiro2spboot.service;

import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.entity.User;

public interface UserService {
    User findByUsername(String name);

    void findUser(String username, PageUtil<User> pageable);

    void saveUser(User user, String roleId);

    void deleteUser(User user);

    int deleteByPrimaryKey(Long uid);

    int insert(User record);

    int insertSelective(User record);

    User selectByPrimaryKey(Long uid);

    int updateByPrimaryKeySelective(User record);

    int updateByPrimaryKey(User record);
}

