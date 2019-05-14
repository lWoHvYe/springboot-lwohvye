package com.springboot.shiro.shiro2spboot.dao;

import com.springboot.shiro.shiro2spboot.entity.User;
import org.springframework.data.repository.CrudRepository;

public interface UserDao extends CrudRepository<User,Long> {
     User findByUsername(String username);
}
