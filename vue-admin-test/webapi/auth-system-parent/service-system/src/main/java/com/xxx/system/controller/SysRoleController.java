package com.xxx.system.controller;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.common.result.Result;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.system.SysRole;
import com.xxx.system.annoation.Log;
import com.xxx.system.enums.BusinessType;
import com.xxx.system.service.SysRoleService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Api(tags = "角色管理接口")
@RestController
@RequestMapping("/admin/system/sysRole")
public class SysRoleController {

    @Autowired
    private SysRoleService sysRoleService;

    //查询所有的记录
    @ApiOperation("查询所有记录")
    @GetMapping("findAll")
    @PreAuthorize("hasAuthority('bnt.sysRole.list')")
    public Result findAll(){
        List<SysRole> list = sysRoleService.list();
        return Result.ok(list);
    }

    /**
     *
     * @param pageNum 页码
     * @param pageSize 数量
     * @param sysRoleQueryVo
     * @return
     */
    @ApiOperation("条件分页查询")
    @GetMapping("{pageNum}/{pageSize}")
    public Result findPageQueryRole(@PathVariable("pageNum") Long pageNum,
                                    @PathVariable("pageSize") Long pageSize,
                                    SysRoleQueryVo sysRoleQueryVo){
        //插件page对象
        Page<SysRole> pageParam = new Page<>(pageNum,pageSize);
        IPage<SysRole> pageModel = sysRoleService.selectPage(pageParam,sysRoleQueryVo);
        return Result.ok(pageModel);
    }

    /**
     * RequestBody不能用get提交 把json格式数据封装到对象里
     * @param sysRole
     * @return
     */
    @Log(title = "角色管理",businessType = BusinessType.INSERT)
    @ApiOperation("添加角色")
    @PostMapping("save")
    public Result saveRole(@RequestBody SysRole sysRole){
        if(sysRole != null){
            boolean save = sysRoleService.save(sysRole);
            if(save) return Result.ok();
        }
        return Result.fail();
    }

    /**
     * 根据id查询角色
     * @param id
     * @return
     */
    @ApiOperation("根据id查询")
    @PostMapping("findRoleById/{id}")
    public Result findRoleById(@PathVariable String id){
        SysRole sysRole = sysRoleService.getById(id);
        if(sysRole != null) return Result.ok(sysRole);
        else return Result.fail().message("获取数据失败");
    }

    /**
     * 修改角色
     * @param sysRole
     * @return
     */
    @Log(title = "角色管理",businessType = BusinessType.UPDATE)
    @ApiOperation("修改角色")
    @PostMapping("update")
    public Result updateRole(@RequestBody SysRole sysRole){
        boolean isSuccess = sysRoleService.updateById(sysRole);
        return isSuccess ? Result.ok() : Result.fail();
    }

    @ApiOperation("批量删除")
    @DeleteMapping("batchRemove")
    public Result batchRemove(@RequestBody List<String> ids){
        return sysRoleService.removeByIds(ids) ? Result.ok() : Result.fail();
    }

    //删除记录
    @ApiOperation("逻辑删除")
    @DeleteMapping("/remove/{id}")
    public Result removeRole(@PathVariable String id){
        boolean b = sysRoleService.removeById(id);
        return b ? Result.ok() : Result.fail();
    }

    @ApiOperation("获取用户角色的数据")
    @GetMapping("/toAssign/{userId}")
    public Result toAssign(@PathVariable String userId){
        Map<String,Object> roleMap = sysRoleService.getRolesByUserId(userId);
        return Result.ok(roleMap);
    }

    @ApiOperation("用户分配角色")
    @PostMapping("doAssign")
    public Result doAssign(@RequestBody AssignRoleVo assignRoleVo){
        sysRoleService.doAssign(assignRoleVo);
        return Result.ok();
    }
}
