package com.xxx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.model.vo.SysOperLogQueryVo;
import com.xxx.system.SysOperLog;
import com.xxx.system.mapper.OperLogMapper;
import com.xxx.system.service.OperLogService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

@Service
public class OperLogServiceImpl extends ServiceImpl<OperLogMapper, SysOperLog> implements OperLogService {

    @Autowired
    private OperLogMapper operLogMapper;

    @Override
    public void saveSysLog(SysOperLog sysOperLog) {
        operLogMapper.insert(sysOperLog);
    }

    @Override
    public IPage<SysOperLog> selectPage(Long pageNum, Long pageSize, SysOperLogQueryVo sysLoginLogQueryVo) {
        Page page = new Page<SysOperLog>(pageNum,pageSize);
        String operName = sysLoginLogQueryVo.getOperName();
        String title = sysLoginLogQueryVo.getTitle();
        String createTimeBegin = sysLoginLogQueryVo.getCreateTimeBegin();
        final String createTimeEnd = sysLoginLogQueryVo.getCreateTimeEnd();

        QueryWrapper<SysOperLog> wrapper = new QueryWrapper<>();
        if(!StringUtils.isEmpty(operName)){
            wrapper.like("oper_name",operName);
        }
        if(!StringUtils.isEmpty(title)){
            wrapper.like("title",title);
        }
        if(!StringUtils.isEmpty(createTimeBegin) && !StringUtils.isEmpty(createTimeEnd)){
            wrapper.ge("create_time",createTimeBegin);
            wrapper.le("create_time",createTimeEnd);
        }
        IPage modelPage = operLogMapper.selectPage(page,wrapper);
        return modelPage;
    }
}
