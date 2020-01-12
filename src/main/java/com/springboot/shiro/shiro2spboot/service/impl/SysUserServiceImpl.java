package com.springboot.shiro.shiro2spboot.service.impl;

import com.springboot.shiro.shiro2spboot.common.datasource.DatabaseType;
import com.springboot.shiro.shiro2spboot.common.datasource.dataSource;
import com.springboot.shiro.shiro2spboot.common.util.PageUtil;
import com.springboot.shiro.shiro2spboot.dao.master.MasterUserMapper;
import com.springboot.shiro.shiro2spboot.dao.slave.SlaveRoleMapper;
import com.springboot.shiro.shiro2spboot.dao.slave.SlaveUserMapper;
import com.springboot.shiro.shiro2spboot.entity.Role;
import com.springboot.shiro.shiro2spboot.entity.User;
import com.springboot.shiro.shiro2spboot.repository.UserDao;
import com.springboot.shiro.shiro2spboot.service.SysUserService;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.util.UUID;

//权限相关，只使用主库
@dataSource(DatabaseType.MASTER)
@Service
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MasterUserMapper masterUserMapper;
    @Autowired
    private SlaveUserMapper slaveUserMapper;
    @Autowired
    private SlaveRoleMapper slaveRoleMapper;

    @Override
    public User findByUsername(String name) {
        return userDao.findByUsername(name);
    }

    @Override
    public void findUser(String username, PageUtil<User> pageUtil) {
        Page<User> userPage = userDao.findUser(username, pageUtil.obtPageable());
        pageUtil.setPageEntity(userPage);
//        return pageUtil;
    }

    /**
     * 授权
     *
     * @param user
     * @param roleId
     */
    @Override
    public void saveUser(User user, String roleId) {
//        页面传密码时，放进行密码相关操作
        if (!StringUtils.isEmpty(user.getPassword())) {
            //    每次改密码都重新生成盐，提高安全性
            String salt =
                    UUID.randomUUID().toString().replace("-", "").toLowerCase();
            //    设置盐
            user.setSalt(salt);
            //     使用用户名+密码并反转的方式作为新密码
            String password = new StringBuilder(user.getUsername() + user.getPassword()).reverse().toString();
            //    使用md5加盐加密
            SimpleHash simpleHash =
                    new SimpleHash("md5", password, user.getCredentialsSalt(), 2);
            //    设置密码
            user.setPassword(simpleHash.toString());
        }
//        页面传的角色Id不为空时进行保存
        if (!StringUtils.isEmpty(roleId)) {
            Role role = new Role();
            role.setId(Long.parseLong(roleId));
            user.setRoles(role);
        }
        userDao.save(user);
    }

    @Override
    public void deleteUser(User user) {
        userDao.delete(user);
    }

    @Override
    public int deleteByPrimaryKey(Long uid) {
        return masterUserMapper.deleteByPrimaryKey(uid);
    }

    @Override
    public int insert(User record) {
        return masterUserMapper.insert(record);
    }

    @Override
    public int insertSelective(User record) {
        return masterUserMapper.insertSelective(record);
    }

    @Override
    public User selectByPrimaryKey(Long uid) {
        return slaveUserMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKeySelective(User record) {
        return masterUserMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return masterUserMapper.updateByPrimaryKey(record);
    }

    @Override
    public User findLoginUser(String username) {
        var user = slaveUserMapper.findByUsername(username);
        if (user != null && user.getRoleId() != null) {
//            获取用户角色信息
            var roles = slaveRoleMapper.selectByPrimaryKey(user.getRoleId());
//            设置用户角色
            user.setRoles(roles);
        }
//        返回结果
        return user;
    }
}

