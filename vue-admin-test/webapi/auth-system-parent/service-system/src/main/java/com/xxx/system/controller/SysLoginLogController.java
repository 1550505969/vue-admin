package com.xxx.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.common.result.Result;
import com.xxx.model.vo.SysLoginLogQueryVo;
import com.xxx.system.SysLoginLog;
import com.xxx.system.SysOperLog;
import com.xxx.system.service.LoginLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("SysLoginLog日志管理")
@RestController
@RequestMapping("/admin/system/sysLoginLog")
public class SysLoginLogController {
    @Autowired
    private LoginLogService loginLogService;

    @ApiOperation("获取分页列表")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result index(@PathVariable Long pageNum,
                        @PathVariable Long pageSize,
                        SysLoginLogQueryVo sysLoginLogQueryVo){
        IPage<SysLoginLog> sysLoginLogIPage = loginLogService.selectPage(pageNum,pageSize,sysLoginLogQueryVo);
        return sysLoginLogIPage != null ? Result.ok(sysLoginLogIPage) : Result.fail().message("获取数据失败");
    }
}
