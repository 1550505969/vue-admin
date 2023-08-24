package com.xxx.system;

import com.xxx.system.service.SysRoleService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.List;

@SpringBootTest
public class SysRoleServiceTest {
    @Autowired
    private SysRoleService sysRoleService;

    @Test
    public void findAll(){
        List<SysRole> list = sysRoleService.list();
        System.out.println(list);
    }

    //添加
    @Test
    public void add(){
        SysRole sysRole = new SysRole();
        sysRole.setRoleName("测试管理员");
        sysRoleService.save(sysRole);
    }

    //修改
    @Test
    public void update(){
        SysRole sysRole = sysRoleService.getById("12132");
        sysRole.setRoleName("小桑");
        sysRoleService.updateById(sysRole);
    }
}
