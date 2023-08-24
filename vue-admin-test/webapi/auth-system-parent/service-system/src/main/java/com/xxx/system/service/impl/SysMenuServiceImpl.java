package com.xxx.system.service.impl;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.xxx.model.vo.AssignMenuVo;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.model.vo.RouterVo;
import com.xxx.system.SysMenu;
import com.xxx.system.SysRoleMenu;
import com.xxx.system.exception.MyException;
import com.xxx.system.mapper.SysMenuMapper;
import com.xxx.system.mapper.SysRoleMenuMapper;
import com.xxx.system.service.SysMenuService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.xxx.system.utils.MenuHelper;
import com.xxx.system.utils.RouterHelper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * <p>
 * 菜单表 服务实现类
 * </p>
 *
 * @author ${author}
 * @since 2023-08-21
 */
@Transactional
@Service
public class SysMenuServiceImpl extends ServiceImpl<SysMenuMapper, SysMenu> implements SysMenuService {

    @Autowired
    private SysRoleMenuMapper sysRoleMenuMapper;

    @Override
    public List<SysMenu> findNodes() {
        //获取所有菜单
        List<SysMenu> sysMenuList = baseMapper.selectList(null);

        //把所有菜单数据转换成要求的数据格式
        List<SysMenu> resultList = MenuHelper.buildTree(sysMenuList);
        return resultList;
    }

    @Override
    public boolean removeMenuById(String id) {
        //查询删除的菜单下是否有子菜单
        //根据id和parent_id的关系进行删除
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("parent_id",id);
        Integer count = baseMapper.selectCount(wrapper);
        if(count > 0){
        //    存在子菜单
            throw new MyException(201,"请先删除子菜单");
        }
        return baseMapper.deleteById(id) > 0 ? true : false;
    }

    @Override
    public List<SysMenu> findMenuByRoleId(String roleId) {
        //获取所有菜单 status=1
        QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("status",1);
        List<SysMenu> menuList = baseMapper.selectList(wrapper);

        //根据角色id查询分配过哪些菜单
        QueryWrapper<SysRoleMenu> wrapper1 = new QueryWrapper<>();
        wrapper1.eq("role_id",roleId);
        List<SysRoleMenu> roleMenuList = sysRoleMenuMapper.selectList(wrapper1);

        //从第二步查询列表中 获取角色分配所有菜单id
        List<String> menuIdList = new ArrayList<>();
        roleMenuList.forEach(item -> {
            menuIdList.add(item.getMenuId());
        });

        //数据处理 isSelect 选中为true
        menuList.forEach(item -> {
            if(menuIdList.contains(item.getId())){
                item.setSelect(true);
            }else {
                item.setSelect(false);
            }
        });

        //转换为树形结构
        List<SysMenu> sysMenus = MenuHelper.buildTree(menuList);
        System.out.println("sysMenus"+sysMenus);

        return sysMenus;
    }

    @Override
    public void doAssign(AssignMenuVo assignMenuVo) {
        //根据角色id删除之前分配的权限
        QueryWrapper<SysRoleMenu> wrapper = new QueryWrapper<>();
        wrapper.eq("role_id",assignMenuVo.getRoleId());
        sysRoleMenuMapper.delete(wrapper);

        //遍历菜单id 进行添加
        List<String> menuIdList = assignMenuVo.getMenuIdList();
        menuIdList.forEach(menuId -> {
            SysRoleMenu sysRoleMenu = new SysRoleMenu();
            sysRoleMenu.setRoleId(assignMenuVo.getRoleId());
            sysRoleMenu.setMenuId(menuId);
            sysRoleMenuMapper.insert(sysRoleMenu);
        });
    }

    @Override
    public List<RouterVo> getUserMenuList(String id) {
        List<SysMenu> sysMenuList = null;
        //判断id是否为1 超级管理员
        if("1".equals(id)){
            QueryWrapper<SysMenu> wrapper = new QueryWrapper<>();
            wrapper.eq("status",1);
            wrapper.orderByAsc("sort_value");
            sysMenuList = baseMapper.selectList(wrapper);
        }else {
            //如果id不是1 其他类型用户 查询权限
            sysMenuList = baseMapper.findMenuListByUserId(id);
        }

        //构建成树形结构
        List<SysMenu> tree = MenuHelper.buildTree(sysMenuList);
        //转换成前端路由要求的格式
        List<RouterVo> routerVoList = RouterHelper.buildRouters(tree);
        System.out.println(routerVoList);
        return routerVoList;
    }

    @Override
    public List<String> getUserButtonList(String id) {
        List<SysMenu> sysMenuList = null;
        //判断是否为管理员
        if("1".equals(id)){
            //查询全部
            sysMenuList =
                    baseMapper.selectList(new QueryWrapper<SysMenu>().eq("status", 1));
        }else {
            sysMenuList = baseMapper.findMenuListByUserId(id);
        }

        List<String> permissionList = new ArrayList<>();
        sysMenuList.forEach(sysMenu -> {
            if(sysMenu.getType() == 2)
                permissionList.add(sysMenu.getPerms());
        });
        return permissionList;
    }
}
