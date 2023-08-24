package com.xxx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.model.vo.SysRoleQueryVo;
import com.xxx.system.SysRole;
import com.xxx.system.SysUserRole;
import com.xxx.system.mapper.SysRoleMapper;
import com.xxx.system.mapper.SysUserRoleMapper;
import com.xxx.system.service.SysRoleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
@Transactional
public class SysRoleServiceImpl extends ServiceImpl<SysRoleMapper, SysRole> implements SysRoleService {
    @Autowired
    private SysUserRoleMapper sysUserRoleMapper;

    @Override
    public IPage<SysRole> selectPage(Page<SysRole> pageParam, SysRoleQueryVo sysRoleQueryVo) {
        IPage<SysRole> pageModel = baseMapper.selectPage(pageParam,sysRoleQueryVo);
        return pageModel;
    }

    //根据用户id获取角色数据
    @Override
    public Map<String, Object> getRolesByUserId(String userId) {
        //获取所有的角色数据
        List<SysRole> sysRoles = baseMapper.selectList(null);
        //根据用户id查询,用户有的角色信息
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",userId);
        List<SysUserRole> userRolesList = sysUserRoleMapper.selectList(wrapper);
        //从userRoles的集合中获取角色id
        List<String> userRoleIds = new ArrayList<>();
        userRolesList.stream().forEach(item -> {
            userRoleIds.add(item.getRoleId());
        });
        //封装到map集合
        Map<String,Object> returnMap = new HashMap<>();
        returnMap.put("allRoles",sysRoles);
        returnMap.put("userRoleIds",userRoleIds);
        return returnMap;
    }

    //用户分配角色
    @Override
    public void doAssign(AssignRoleVo assignRoleVo) {
        //根据用户id删除之前分配的角色信息
        QueryWrapper<SysUserRole> wrapper = new QueryWrapper<>();
        wrapper.eq("user_id",assignRoleVo.getUserId());
        sysUserRoleMapper.delete(wrapper);

        //获取所有角色id，添加到角色用户关系表中
        List<String> roleIdList = assignRoleVo.getRoleIdList();
        roleIdList.forEach(item -> {
            SysUserRole sysUserRole = new SysUserRole();
            sysUserRole.setUserId(assignRoleVo.getUserId());
            sysUserRole.setRoleId(item);
            sysUserRoleMapper.insert(sysUserRole);
        });
    }
}
