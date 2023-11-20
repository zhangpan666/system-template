package com.gallery.manage.admin.service.impl;

import com.gallery.manage.admin.pojo.MenuVO;
import com.gallery.manage.admin.service.AdminIndexService;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.*;
import com.gallery.manage.common.service.*;
import com.gallery.manage.common.util.LoginUtil;
import com.gallery.manage.common.util.MenuUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.light.redis.util.RedisStringHashUtil;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class AdminIndexServiceImpl implements AdminIndexService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    MenuService menuService;

    @Autowired
    RoleService roleService;

    @Autowired
    SysUserService userService;



    @Override
    public List<MenuVO> getMenuList() {
        Subject subject = SecurityUtils.getSubject();
        String username = subject.getPrincipal().toString();
        Set<Menu> menuList = MenuUtil.getMenuList();
        if (SystemUtil.isAdmin(username)) {
            return getAllMenuList(menuList);
        } else {
            Long adminLoginUserId = LoginUtil.getAdminLoginUserId();
            SysUserRole sysUserRole = sysUserRoleService.getUserRoleDetailByUserId(adminLoginUserId);
            if (sysUserRole == null) {
                return null;
            }
            if (SystemUtil.isAdminRole(sysUserRole.getRoleCode())) {
                return getAllMenuList(menuList);
            }
            List<Permission> permissionList = permissionService.getPermissionListByRoleId(sysUserRole.getRoleId());
            if (CollectionUtils.isEmpty(permissionList)) {
                return null;
            }
            List<Long> menuIdList = permissionList.stream().map(permission -> permission.getMenuId()).collect(Collectors.toList());
            List<MenuVO> menuVOList = menuList.stream().map(menu -> {
                MenuVO menuVO = MenuUtil.generateMenuVO(menu);
                Set<Menu> subMenuList = menu.getChildMenuList();
                if (!CollectionUtils.isEmpty(subMenuList)) {
                    List<MenuVO> childMenuList = subMenuList.stream().filter(menu13 -> menuIdList.contains(menu13.getId()))
                            .map(menu12 -> MenuUtil.generateMenuVO(menu12)).collect(Collectors.toList());
                    menuVO.setChildren(childMenuList);
                }
                return menuVO;
            }).filter(menuVO -> {
                List<MenuVO> children = menuVO.getChildren();
                if (!CollectionUtils.isEmpty(children)) {
                    return true;
                }
                return false;
            }).collect(Collectors.toList());
            return menuVOList;
        }
    }

    @Override
    public Map<String, Object> stat() {
        Map<String, Object> stat=new HashMap<>();
        int adminCount = userService.getCount(new SysUser());
        stat.put("adminCount",adminCount);
        String key = ProjectConstant.RedisKey.ONLINE_ADMIN.getKeyPrefix();
        Long adminOnlineCount = RedisStringHashUtil.size(key);
        stat.put("adminOnlineCount",adminOnlineCount);
        stat.put("userCount",0);
        return stat;
    }

    private List<MenuVO> getAllMenuList(Set<Menu> menuList) {
        return menuList.stream().map(menu -> {
            MenuVO menuVO = MenuUtil.generateMenuVO(menu);
            Set<Menu> subMenuList = menu.getChildMenuList();
            if (!CollectionUtils.isEmpty(subMenuList)) {
                List<MenuVO> children = subMenuList.stream().map(menu1 -> MenuUtil.generateMenuVO(menu1)).collect(Collectors.toList());
                menuVO.setChildren(children);
            }
            return menuVO;
        }).collect(Collectors.toList());
    }

}
