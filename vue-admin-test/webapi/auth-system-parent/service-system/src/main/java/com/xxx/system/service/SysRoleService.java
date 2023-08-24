package com.xxx.system.service;

import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.system.SysRole;
import org.springframework.stereotype.Service;

import java.util.Map;

public interface SysRoleService extends IService<SysRole> {
    IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo);

    Map<String, Object> getRolesByUserId(String userId);

    void doAssign(AssignRoleVo assignRoleVo);
}
