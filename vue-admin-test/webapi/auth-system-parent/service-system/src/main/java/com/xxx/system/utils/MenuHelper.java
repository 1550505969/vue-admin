package com.xxx.system.utils;

import com.xxx.system.SysMenu;

import java.util.ArrayList;
import java.util.List;

public class MenuHelper {

    public static List<SysMenu> buildTree(List<SysMenu> sysMenuList){
        List<SysMenu> trees = new ArrayList<>();

        for (SysMenu sysMenu : sysMenuList) {
            if(sysMenu.getParentId().longValue() == 0){
                trees.add(findChildren(sysMenu,sysMenuList));
            }
        }
        System.out.println(trees);
        return trees;
    }

    private static SysMenu findChildren(SysMenu sysMenu,List<SysMenu> sysMenuList){
        sysMenu.setChildren(new ArrayList<>());

        for (SysMenu item:
             sysMenuList) {

            //比对
            if(Long.parseLong(sysMenu.getId()) == item.getParentId()){
                if(sysMenu.getChildren() == null) sysMenu.setChildren(new ArrayList<>());
                sysMenu.getChildren().add(findChildren(item,sysMenuList));
            }
        }
        return sysMenu;
    }
}
