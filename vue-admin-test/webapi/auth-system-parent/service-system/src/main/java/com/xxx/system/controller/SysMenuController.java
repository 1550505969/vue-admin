package com.xxx.system.controller;


import com.baomidou.mybatisplus.extension.api.R;
import com.xxx.common.result.Result;
import com.xxx.model.vo.AssignMenuVo;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.system.SysMenu;
import com.xxx.system.service.SysMenuService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * <p>
 * 菜单表 前端控制器
 * </p>
 *
 * @author ${author}
 * @since 2023-08-21
 */
@Api("菜单管理")
@RestController
@RequestMapping("/admin/system/sysMenu")
public class SysMenuController {
    @Autowired
    private SysMenuService sysMenuService;

    @ApiOperation("菜单列表")
    @GetMapping("/findNodes")
    public Result findNodes(){
        List<SysMenu> list = sysMenuService.findNodes();
        return list != null ? Result.ok(list) : Result.fail().message("获取数据失败");
    }

    @ApiOperation("添加菜单")
    @PostMapping("save")
    public Result save(@RequestBody SysMenu sysMenu){
        boolean save = sysMenuService.save(sysMenu);
        return save ? Result.ok() : Result.fail();
    }

    @ApiOperation("根据id查询菜单")
    @GetMapping("findNode/{id}")
    public Result findNode(@PathVariable String id){
        SysMenu sysMenu = sysMenuService.getById(id);
        return sysMenu != null ? Result.ok(sysMenu) : Result.fail().message("获取数据失败");
    }

   @ApiOperation("修改菜单")
   @PostMapping("update")
   public Result update(@RequestBody SysMenu sysMenu){
       boolean update = sysMenuService.updateById(sysMenu);
       return update ? Result.ok() : Result.fail();
   }

   @ApiOperation("删除菜单")
   @DeleteMapping("remove/{id}")
   public Result remove(@PathVariable String id){
       boolean update = sysMenuService.removeMenuById(id);
       return update ? Result.ok() : Result.fail();
   }

   @ApiOperation("根据角色获取分配的菜单")
   @GetMapping("toAssign/{roleId}")
   public Result toAssign(@PathVariable String roleId){
        List<SysMenu> list = sysMenuService.findMenuByRoleId(roleId);
        return Result.ok(list);
   }

   @ApiOperation("给角色分配权限")
   @PostMapping("doAssign")
   public Result doAssign(@RequestBody AssignMenuVo assignMenuVo){
        sysMenuService.doAssign(assignMenuVo);
        return Result.ok();
   }
}

