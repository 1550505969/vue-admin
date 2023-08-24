package com.xxx.system.controller;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.common.result.Result;
import com.xxx.common.utils.MD5;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.model.vo.SysUserQueryVo;
import com.xxx.system.SysRole;
import com.xxx.system.SysUser;
import com.xxx.system.service.SysUserService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 用户表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-08-20
 */
@Api("用户管理接口")
@RestController
@RequestMapping("/admin/system/sysUser")
public class SysUserController {

    private final SysUserService sysUserService;

    //推荐使用该方式注入依赖
    @Autowired
    public SysUserController(SysUserService sysUserService){
        this.sysUserService = sysUserService;
    }

    @ApiOperation("用户列表")
    @GetMapping("/{pageNum}/{pageSize}")
    public Result list(@PathVariable("pageNum") Long pageNum,
                       @PathVariable("pageSize") Long pageSize,
                       SysUserQueryVo sysUserQueryVo){
        Page<SysUser> sysUserPage = new Page<>(pageNum, pageSize);
        IPage<SysUser> pageModel = sysUserService.selectPage(sysUserPage,sysUserQueryVo);
        return pageModel != null ? Result.ok(pageModel) : Result.fail().message("获取数据失败");
    }

    @ApiOperation("添加用户")
    @PostMapping("save")
    public Result save(@RequestBody SysUser sysUser){
        //把输入的密码进行加密 使用MD5
        String encrypt = MD5.encrypt(sysUser.getPassword());
        sysUser.setPassword(encrypt);
        boolean save = sysUserService.save(sysUser);
        return save ? Result.ok() : Result.fail().message("添加用户失败");
    }

    @ApiOperation("根据id查询")
    @PostMapping("/getUser/{id}")
    public Result getUser(@PathVariable String id){
        SysUser sysUser = sysUserService.getById(id);
        return sysUser != null ? Result.ok(sysUser) : Result.fail().message("获取用户数据失败");
    }

    @ApiOperation("修改用户")
    @PostMapping("update")
    public Result update(@RequestBody SysUser sysUser){
        boolean update = sysUserService.updateById(sysUser);
        return update ? Result.ok() : Result.fail().message("修改用户失败");
    }

    //删除记录
    @ApiOperation("用户删除")
    @DeleteMapping("/remove/{id}")
    public Result removeUser(@PathVariable String id){
        boolean remove = sysUserService.removeById(id);
        return remove ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> ids){
        return sysUserService.removeByIds(ids) ? Result.ok() : Result.fail();
    }

    @ApiOperation("更改用户状态")
    @GetMapping("updateStatus/{id}/{status}")
    public Result updateStatus(@PathVariable String id, @PathVariable Integer status) {
        boolean update = sysUserService.updateStatus(id, status);
        return update ? Result.ok() : Result.fail().message("修改失败");
    }
}

