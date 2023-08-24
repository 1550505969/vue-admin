package com.xxx.system;

import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.system.mapper.SysRoleMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.Arrays;
import java.util.List;

@SpringBootTest()
public class SysRoleMapperTest {
    @Autowired
    private SysRoleMapper sysRoleMapper;

    //查询表所有记录
    @Test
    public void select(){
        List<SysRole> sysRoles = sysRoleMapper.selectList(null);
        for (SysRole s:
             sysRoles) {
            System.out.println(s);
        }
    }

    // 添加操作
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("testRole2");
        sysRole.setRoleCode("testManager2");
        sysRole.setDescription("测试角色2");
        int rows = sysRoleMapper.insert(sysRole);
        System.out.println(rows);
    }

    // 修改操作
    @Test
    public void update(){
        SysRole sysRole = sysRoleMapper.selectById(12131);
        sysRole.setRoleName("张四");
        sysRoleMapper.updateById(sysRole);
    }

    // 删除 逻辑删除
    @Test
    public void delete(){
        sysRoleMapper.deleteById("1692762612061806594");
    }

    //批量删除
    @Test
    public void deleteBatch(){
        sysRoleMapper.deleteBatchIds(Arrays.asList("1692762612061806594","1692764689320148993"));
    }

    //条件查询
    @Test
    public void selectByWrapper(){
        QueryWrapper<SysRole> wrapper = new QueryWrapper<>();
        // wrapper.eq("role_name","用户管理员");
        wrapper.like("role_name","管理员");
        List<SysRole> sysRoles = sysRoleMapper.selectList(wrapper);
        System.out.println(sysRoles);
    }

    //条件删除
    @Test
    public void deleteByWrapper(){
        LambdaQueryWrapper<SysRole> wrapper = new LambdaQueryWrapper<>();
        wrapper.eq(SysRole::getRoleName,"张四");
        sysRoleMapper.delete(wrapper);
    }
}
