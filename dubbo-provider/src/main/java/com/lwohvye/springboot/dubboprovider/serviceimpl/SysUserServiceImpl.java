package com.lwohvye.springboot.dubboprovider.serviceimpl;

import com.lwohvye.springboot.dubbointerface.common.util.HttpContextUtil;
import com.lwohvye.springboot.dubbointerface.common.util.PageUtil;
import com.lwohvye.springboot.dubbointerface.entity.User;
import com.lwohvye.springboot.dubbointerface.entity.UserLog;
import com.lwohvye.springboot.dubbointerface.service.SysUserService;
import com.lwohvye.springboot.dubboprovider.dao.master.MasterUserLogMapper;
import com.lwohvye.springboot.dubboprovider.dao.master.MasterUserMapper;
import com.lwohvye.springboot.dubboprovider.dao.slave.SlaveRoleMapper;
import com.lwohvye.springboot.dubboprovider.dao.slave.SlaveUserMapper;
import com.lwohvye.springboot.dubboprovider.repository.UserDao;
import org.apache.dubbo.config.annotation.Service;
import org.apache.shiro.crypto.hash.SimpleHash;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import java.util.UUID;

//这里的@Service注解使用dubbo的，需注意
@Service(version = "${lwohvye.service.version}")
@Component
public class SysUserServiceImpl implements SysUserService {

    @Autowired
    private UserDao userDao;
    @Autowired
    private MasterUserMapper masterUserMapper;
    @Autowired
    private SlaveUserMapper slaveUserMapper;
    @Autowired
    private SlaveRoleMapper slaveRoleMapper;
    @Autowired
    private MasterUserLogMapper masterUserLogMapper;

    @Override
    public PageUtil<User> findUser(String username, PageUtil<User> pageUtil) {
        Page<User> userPage = userDao.findUser(username, pageUtil.obtPageable());
        pageUtil.setPageEntity(userPage);
        return pageUtil;
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
    public int insertSelective(User user) {
        //        页面传密码时，放进行密码相关操作
        setUserParams(user);
        return masterUserMapper.insertSelective(user);
    }

    @Override
    public User selectByPrimaryKey(Long uid) {
        return slaveUserMapper.selectByPrimaryKey(uid);
    }

    @Override
    public int updateByPrimaryKeySelective(User user) {
        setUserParams(user);
        return masterUserMapper.updateByPrimaryKeySelective(user);
    }

    private void setUserParams(User user) {
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
    }

    @Override
    public int updateByPrimaryKey(User record) {
        return masterUserMapper.updateByPrimaryKey(record);
    }

    //    开启事务，可以配置事务的参数
    //    PROPAGATION_REQUIRED： 如果当前存在事务，则加入该事务；如果当前没有事务，则创建一个新的事务
    //    ISOLATION_DEFAULT: 使用后端数据库默认的隔离级别，Mysql 默认采用的 REPEATABLE_READ隔离级别 Oracle 默认采用的 READ_COMMITTED隔离级别
    //    如果不配置rollbackFor属性,那么事务只会在遇到RuntimeException的时候才会回滚,加上rollbackFor=Exception.class,可以让事务在遇到非运行时异常时也回滚
    @Transactional(propagation = Propagation.REQUIRED, isolation = Isolation.DEFAULT, rollbackFor = Exception.class, readOnly = false, timeout = -1)
    @Override
    public User findLoginUser(String username) {
        var user = slaveUserMapper.findByUsername(username);
        if (user != null && user.getRoleId() != null) {
//            获取用户角色信息
            var roles = slaveRoleMapper.selectByPrimaryKey(user.getRoleId());
//            设置用户角色
            user.setRoles(roles);

//            加入日志中
            UserLog log = new UserLog();
            log.setActType("类名 :" + this.getClass().getName() + " ; 方法名 : login ; 方法描述 : 登陆系统");// 操作说明
            log.setUsername(user.getUsername());
//            获取并设置参数
            String actParams = " 用户名 : " + username + " : 加密密码 : " + user.getPassword() + " : 盐 : " + user.getCredentialsSalt();
            log.setActParams(actParams);
            String ip = null;
            try {
                ip = HttpContextUtil.getIpAddress();
            } catch (Exception e) {
                e.printStackTrace();
            }
            log.setIpAddr(ip);
            masterUserLogMapper.insertSelective(log);// 添加日志记录
        }
//        返回结果
        return user;
    }
}

