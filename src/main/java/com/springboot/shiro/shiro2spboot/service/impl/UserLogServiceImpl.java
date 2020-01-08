package com.springboot.shiro.shiro2spboot.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.springboot.shiro.shiro2spboot.common.datasource.DatabaseType;
import com.springboot.shiro.shiro2spboot.common.datasource.dataSource;
import com.springboot.shiro.shiro2spboot.dao.UserLogMapper;
import com.springboot.shiro.shiro2spboot.entity.UserLog;
import com.springboot.shiro.shiro2spboot.service.UserLogService;
import org.apache.ibatis.session.RowBounds;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class UserLogServiceImpl implements UserLogService {

    @Resource
    private UserLogMapper userLogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return userLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserLog record) {
        return userLogMapper.insert(record);
    }

    @Override
    public int insertSelective(UserLog record) {
        return userLogMapper.insertSelective(record);
    }

    @Override
    public UserLog selectByPrimaryKey(Integer id) {
        return userLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLog record) {
        return userLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLog record) {
        return userLogMapper.updateByPrimaryKey(record);
    }

    @dataSource(DatabaseType.SLAVE)
    @Override
    public PageInfo<UserLog> list(String username, String startDate, String endDate, int page, int pageSize) {
        PageHelper.startPage(page, pageSize);
        return new PageInfo<>(userLogMapper.list(username, startDate, endDate));
    }

    @dataSource(DatabaseType.SLAVE)
    @Override
    public int selectCount(String username, String startDate, String endDate) {
        return userLogMapper.selectCount(username, startDate, endDate);
    }
}
