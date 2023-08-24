package com.xxx.system.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.xxx.model.vo.AssignMenuVo;
import com.xxx.model.vo.AssignRoleVo;
import com.xxx.model.vo.RouterVo;
import com.xxx.system.SysMenu;

import java.util.List;

/**
 * <p>
 * 菜单表 服务类
 * </p>
 *
 * @author ${author}
 * @since 2023-08-21
 */
public interface SysMenuService extends IService<SysMenu> {

    List<SysMenu> findNodes();

    boolean removeMenuById(String id);

    List<SysMenu> findMenuByRoleId(String roleId);

    void doAssign(AssignMenuVo assignMenuVo);

    List<RouterVo> getUserMenuList(String id);

    List<String> getUserButtonList(String id);
}
