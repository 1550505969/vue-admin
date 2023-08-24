package com.xxx.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.xxx.common.result.Result;
import com.xxx.model.vo.SysOperLogQueryVo;
import com.xxx.system.SysOperLog;
import com.xxx.system.service.OperLogService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api("操作日志管理")
@RestController
@RequestMapping("/admin/system/sysOperLog")
public class SysOperLogController {
    @Autowired
    private OperLogService operLogService;

    @ApiOperation("获取分页列表")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result index(@PathVariable Long pageNum,
                        @PathVariable Long pageSize,
                        SysOperLogQueryVo sysLoginLogQueryVo){
        IPage<SysOperLog> sysOperLogIPage = operLogService.selectPage(pageNum,pageSize,sysLoginLogQueryVo);
        return sysOperLogIPage != null ? Result.ok(sysOperLogIPage) : Result.fail().message("获取数据失败");
    }
}
