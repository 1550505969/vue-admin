package com.xxx.system.service.impl;


import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.xxx.common.utils.JwtHelper;
import com.xxx.common.utils.MD5;
import com.xxx.model.vo.LoginVo;
import com.xxx.model.vo.RouterVo;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.model.vo.SysUserQueryVo;
import com.xxx.system.SysRole;
import com.xxx.system.SysUser;
import com.xxx.system.exception.MyException;
import com.xxx.system.mapper.SysUserMapper;
import com.xxx.system.service.SysMenuService;
import com.xxx.system.service.SysUserService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 用户表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-08-20
 */
@Transactional
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {

    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public IPage<SysUser> selectPage(Page<SysUser> sysUserPage, SysUserQueryVo sysUserQueryVo) {
        return baseMapper.selectPage(sysUserPage,sysUserQueryVo);
    }

    @Override
    public boolean updateStatus(String id, Integer status) {
        SysUser sysUser = baseMapper.selectById(id);
        sysUser.setStatus(status);
        int update = baseMapper.updateById(sysUser);
        return update != 0 ? true : false;
    }

    @Override
    public Map<String, Object> login(LoginVo loginVo) {
        //根据username查询数据
        // QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // wrapper.eq("username",loginVo.getUsername());
        // SysUser sysUser = baseMapper.selectOne(wrapper);
        SysUser sysUser = getUserInfoByUsername(loginVo.getUsername());

        //如果查询为空
        if(sysUser == null){
            throw new MyException(20001,"用户不存在");
        }

        //判断密码是否一致
        if(!sysUser.getPassword().equals(MD5.encrypt(loginVo.getPassword()))){
            throw new MyException(20001,"密码不正确");
        }

        //判断用户是否可用
        if(sysUser.getStatus() == 0){
            throw new MyException(20001,"账户被禁用");
        }

        //根据userId和username生成token字符串 通过map返回
        String token = JwtHelper.createToken(sysUser.getId(), sysUser.getUsername());
        Map<String,Object> map = new HashMap<>();
        map.put("token",token);
        return map;
    }

    @Override
    public Map<String, Object> getUserInfo(String username) {
        //根据username查询用户基本信息
        // QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        // wrapper.eq("username",username);
        // SysUser sysUser = baseMapper.selectOne(wrapper);
        SysUser sysUser = getUserInfoByUsername(username);

        //根据userid查询菜单权限值
        List<RouterVo> routerVoList = sysMenuService.getUserMenuList(sysUser.getId());
        //根据userid查询按钮权限值
        List<String> permsList = sysMenuService.getUserButtonList(sysUser.getId());
        System.out.println("permsList" + permsList);

        Map<String, Object> result = new HashMap<>();
        result.put("name",username);
        result.put( "avatar","https://wpimg.wallstcn.com/f778738c-e4f8-4870-b634-56703b4acafe.gif");
        result.put("roles","['admin']");
        //菜单权限数据
        result.put("routers",routerVoList);
        //按钮权限数据
        result.put("buttons",permsList);
        return result;
    }

    @Override
    public SysUser getUserInfoByUsername(String username) {
        //根据username查询用户基本信息
        QueryWrapper<SysUser> wrapper = new QueryWrapper<>();
        wrapper.eq("username",username);
        SysUser sysUser = baseMapper.selectOne(wrapper);
        return sysUser;
    }
}
