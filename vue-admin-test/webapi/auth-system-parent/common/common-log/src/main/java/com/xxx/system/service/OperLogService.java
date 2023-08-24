package com.xxx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.model.vo.SysOperLogQueryVo;
import com.xxx.system.SysOperLog;

public interface OperLogService {
    void saveSysLog(SysOperLog sysOperLog);

    IPage<SysOperLog> selectPage(Long pageNum, Long pageSize, SysOperLogQueryVo sysLoginLogQueryVo);
}
