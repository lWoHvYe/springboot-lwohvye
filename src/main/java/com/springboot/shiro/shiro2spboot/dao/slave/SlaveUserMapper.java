package com.springboot.shiro.shiro2spboot.dao.slave;

import com.springboot.shiro.shiro2spboot.entity.User;
import org.apache.ibatis.annotations.Mapper;

import java.util.List;

@Mapper
public interface SlaveUserMapper {
    User selectByPrimaryKey(Long uid);

    List<User> selectByAll(User user);

}