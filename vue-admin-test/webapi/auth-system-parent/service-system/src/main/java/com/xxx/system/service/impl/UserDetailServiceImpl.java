package com.xxx.system.service.impl;

import com.xxx.system.SysUser;
import com.xxx.system.custom.CustomUser;
import com.xxx.system.service.SysMenuService;
import com.xxx.system.service.SysUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Component
public class UserDetailServiceImpl implements UserDetailsService {
    @Autowired
    private SysUserService sysUserService;
    @Autowired
    private SysMenuService sysMenuService;

    @Override
    public UserDetails loadUserByUsername(String s) throws UsernameNotFoundException {
        SysUser sysUser = sysUserService.getUserInfoByUsername(s);
        if(sysUser == null){
            throw new UsernameNotFoundException("用户不存在");
        }
        if(sysUser.getStatus() == 0){
            throw new RuntimeException("用户被禁用了");
        }
        //根据用户id查询操作权限数据
        List<String> userButtonList = sysMenuService.getUserButtonList(sysUser.getId());
        //转换成security要求的格式的数据
        List<SimpleGrantedAuthority> authorities = new ArrayList<>();

        userButtonList.forEach(item -> {
            authorities.add(new SimpleGrantedAuthority(item.trim()));
        });
        return new CustomUser(sysUser,authorities);
    }
}
