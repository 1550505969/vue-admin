package com.xxx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.model.vo.SysLoginLogQueryVo;
import com.xxx.system.SysLoginLog;

public interface LoginLogService {
    void recordLoginLog(String username, Integer status, String ipaddr, String message);

    IPage<SysLoginLog> selectPage(Long pageNum, Long pageSize, SysLoginLogQueryVo sysLoginLogQueryVo);
}
