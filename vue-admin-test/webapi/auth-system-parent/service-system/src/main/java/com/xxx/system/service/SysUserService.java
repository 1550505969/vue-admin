package com.xxx.system.service;


import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.model.vo.LoginVo;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.model.vo.SysUserQueryVo;
import com.xxx.system.SysRole;
import com.xxx.system.SysUser;

import java.util.Map;

/**
 * <p>
 * 用户表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-08-20
 */
public interface SysUserService extends IService<SysUser> {

    IPage<SysUser> selectPage(Page<SysUser> sysRolePage, SysUserQueryVo sysUserQueryVo);

    boolean updateStatus(String id, Integer status);

    Map<String, Object> login(LoginVo loginVo);

    Map<String, Object> getUserInfo(String username);

    SysUser getUserInfoByUsername(String username);
}
