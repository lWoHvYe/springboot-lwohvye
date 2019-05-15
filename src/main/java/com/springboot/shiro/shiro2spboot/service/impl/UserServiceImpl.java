package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.dao.UserDao;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.entity.UserRole;
import com.springboot.shiro.shiro2spboot.service.UserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
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
    public void save(User user, String roleId) {
//      使用md5加盐加密
        SimpleHash simpleHash = new SimpleHash("md5", user.getPassword(), user.getCredentialsSalt(), 2);
        user.setPassword(simpleHash.toString());
//        页面传的角色Id不为空时进行保存
        if (!StringUtils.isEmpty(roleId)) {
            saveRoleId(user,roleId);
        }
        userDao.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    /**
     * 授权,为user添加对应的roles集合后，框架会自动先将user_role表中uid为相关的全删除，
     * 再将集合中的role_id插入表中
     * @param user
     * @param roleId
     */
    private void saveRoleId(User user, String roleId) {
        List<Role> roles = new ArrayList<>();
        if (roleId.contains(",")){
            String[] roleIdArray = roleId.split(",");
            for (String id : roleIdArray) {
                Role role = new Role();
                role.setId(Long.parseLong(id));
                roles.add(role);
            }
        }else {
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            roles.add(role);
        }
        user.setRoles(roles);
    }
}
