package com.lwohvye.springboot.dubboprovider.serviceimpl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lwohvye.springboot.dubbointerface.entity.UserLog;
import com.lwohvye.springboot.dubbointerface.service.UserLogService;
import com.lwohvye.springboot.dubboprovider.dao.master.MasterUserLogMapper;
import com.lwohvye.springboot.dubboprovider.dao.slave.SlaveUserLogMapper;
import org.apache.dubbo.config.annotation.Service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

//这里的@Service注解使用dubbo的，需注意
@Service(version = "${lwohvye.service.version}")
@Component
public class UserLogServiceImpl implements UserLogService {

    @Autowired
    private MasterUserLogMapper masterUserLogMapper;
    @Autowired
    private SlaveUserLogMapper slaveUserLogMapper;

    @Override
    public int deleteByPrimaryKey(Integer id) {
        return masterUserLogMapper.deleteByPrimaryKey(id);
    }

    @Override
    public int insert(UserLog record) {
        return masterUserLogMapper.insert(record);
    }

    @Override
    public int insertSelective(UserLog record) {
        return masterUserLogMapper.insertSelective(record);
    }

    @Override
    public UserLog selectByPrimaryKey(Integer id) {
        return slaveUserLogMapper.selectByPrimaryKey(id);
    }

    @Override
    public int updateByPrimaryKeySelective(UserLog record) {
        return masterUserLogMapper.updateByPrimaryKeySelective(record);
    }

    @Override
    public int updateByPrimaryKey(UserLog record) {
        return masterUserLogMapper.updateByPrimaryKey(record);
    }

    @Override
    public PageInfo<UserLog> list(String username, String startDate, String endDate, int page, int pageSize) {
        return PageHelper.startPage(page, pageSize, "act_time desc").doSelectPageInfo(
                () -> slaveUserLogMapper.list(username, startDate, endDate)
        );
    }

}
