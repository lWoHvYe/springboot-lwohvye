package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.dao.UserDao;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }

    @Override
    public List<User> findUser(String username, Pageable pageable) {
        return userDao.findUser(username,pageable);
    }

    @Override
    public void save(User user) {
//      使用md5加盐加密
        SimpleHash simpleHash = new SimpleHash("md5", user.getPassword(), user.getCredentialsSalt(), 2);
        user.setPassword(simpleHash.toString());
        userDao.save(user);

    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }
}
