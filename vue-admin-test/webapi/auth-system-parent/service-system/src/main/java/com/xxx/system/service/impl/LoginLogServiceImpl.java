package com.xxx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.model.vo.SysLoginLogQueryVo;
import com.xxx.system.SysLoginLog;
import com.xxx.system.mapper.LoginLogMapper;
import com.xxx.system.service.LoginLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class LoginLogServiceImpl implements LoginLogService {

    @Autowired
    private LoginLogMapper loginLogMapper;

    @Override
    public void recordLoginLog(String username, Integer status, String ipaddr, String message) {
        SysLoginLog sysLoginLog = new SysLoginLog();
        sysLoginLog.setUsername(username);
        sysLoginLog.setStatus(status);
        sysLoginLog.setIpaddr(ipaddr);
        sysLoginLog.setMsg(message);
        loginLogMapper.insert(sysLoginLog);
    }

    @Override
    public IPage<SysLoginLog> selectPage(Long pageNum, Long pageSize, SysLoginLogQueryVo sysLoginLogQueryVo) {
        //创建分页对象
        Page<SysLoginLog> page = new Page(pageNum,pageSize);
        //获取条件
        String username = sysLoginLogQueryVo.getUsername();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();

        //封装条件
        QueryWrapper<SysLoginLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(username)){
            wrapper.like("username",username);
        }
        if(!StringUtils.isEmpty(createTimeBegin) && !StringUtils.isEmpty(createTimeEnd)){
            wrapper.ge("create_time",createTimeBegin);
            wrapper.le("create_time",createTimeEnd);
        }

        //调用mapper方法
        IPage pageModel = loginLogMapper.selectPage(page, wrapper);
        return pageModel;
    }
}
