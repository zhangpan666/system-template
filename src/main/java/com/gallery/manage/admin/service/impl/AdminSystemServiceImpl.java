package com.gallery.manage.admin.service.impl;

import com.alibaba.fastjson.JSON;
import com.gallery.manage.admin.pojo.AdminPermissionVO;
import com.gallery.manage.admin.service.AdminSystemService;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.*;
import com.gallery.manage.common.service.*;
import com.gallery.manage.common.util.MenuUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import com.light.core.utils.PageUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.web.servlet.ModelAndView;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class AdminSystemServiceImpl implements AdminSystemService {

    @Autowired
    PermissionService permissionService;

    @Autowired
    SysUserRoleService sysUserRoleService;

    @Autowired
    RoleService roleService;

    @Autowired
    ConfigurationService configurationService;

    @Autowired
    OperationRecordService operationRecordService;

    @Autowired
    CacheService cacheService;


    @Override
    public PageInfo<Role> listRole(Page page, Role role) {
        return PageUtil.query(page, role, role1 -> roleService.query(role1));
    }

    @Override
    public ModelAndView getPermissionDetailPage(Long id, ModelAndView modelAndView) {
        Role role = roleService.getById(id);
        List<Long> hasMenuList = new ArrayList<>();
        List<String> hasPermissions = new ArrayList<>();
        Set<Menu> menuList = MenuUtil.getMenuList();
        if (role != null) {
            List<Permission> permissionList = permissionService.getPermissionListByRoleId(id);
            if (!CollectionUtils.isEmpty(permissionList)) {
                permissionList.stream().forEach(permission -> {
                    Long menuId = permission.getMenuId();
                    String[] permissionArray = permission.getPermission().split(",");
                    hasMenuList.add(menuId);
                    for (String permissionStr : permissionArray) {
                        hasPermissions.add(permissionStr);
                    }
                });
            }
            modelAndView.addObject("roleCode", role.getRoleCode());
            modelAndView.addObject("roleId", role.getId());
        }
        modelAndView.addObject("menuList", menuList);
        modelAndView.addObject("hasMenuList", hasMenuList);
        modelAndView.addObject("hasPermissions", hasPermissions);
        modelAndView.addObject("admin", SystemUtil.getAdminRole());
        return modelAndView;
    }

    @Override
    public boolean saveRole(Role role) {
        return roleService.save(role);
    }

    @Override
    public boolean deleteRole(Long id) {

        boolean removeById = roleService.removeById(id);
        if (removeById) {
            cacheService.clearSysUserAuthorization();
        }
        return removeById;

    }

    @Override
    public CommonResult savePermission(AdminPermissionVO adminPermissionVO) {
        Long roleId = adminPermissionVO.getRoleId();
        String permissions = adminPermissionVO.getPermissionList();
        List<Permission> permissionList = JSON.parseArray(permissions, Permission.class);
        Role role = roleService.getById(roleId);
        if (role == null || role.getRoleCode().equals(SystemUtil.getAdminRole())) {
            return CommonResult.businessWrong();
        }
        permissionService.deleteByRoleId(roleId);
        if (!CollectionUtils.isEmpty(permissionList)) {
            permissionService.saveBatch(permissionList, 10);
        }
        cacheService.clearSysUserAuthorization();
        return CommonResult.success();
    }

    @Override
    public Role getRoleDetail(Long id) {
        return roleService.getById(id);
    }

    @Override
    public void updateRole(Role role) {
        roleService.updateById(role);
        cacheService.clearSysUserAuthorization();
    }

    @Override
    public PageInfo<Configuration> listConfiguration(Page page, Configuration configuration) {
        configuration.setDisplay(ProjectConstant.COMMON_STATUS_AVAILABLE);
        return PageUtil.query(page, configuration, configurationService::query);
    }

    @Override
    public Configuration getConfigurationById(Long id) {
        Configuration configuration = configurationService.getById(id);
        if (configuration != null) {
            Byte display = configuration.getDisplay();
            if (display != ProjectConstant.COMMON_STATUS_AVAILABLE) {
                return null;
            }
        }
        return configuration;
    }

    @Override
    public CommonResult updateConfigurationById(Configuration configuration) {
        String code = configuration.getCode();
        Configuration configurationServiceByCode = configurationService.getByCode(code);
        if (configurationServiceByCode != null && !configurationServiceByCode.getId().equals(configuration.getId())) {
            return CommonResult.businessWrong("该常量已存在");
        }
        boolean update = configurationService.updateById(configuration);
        if (update) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public CommonResult<Object> addConfiguration(Configuration configuration) {
        String code = configuration.getCode();
        Configuration configurationServiceByCode = configurationService.getByCode(code);
        if (configurationServiceByCode != null) {
            return CommonResult.businessWrong("该常量已存在");
        }
        boolean save = configurationService.save(configuration);
        if (save) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @Override
    public boolean deleteConfigurationById(Long id) {
        return configurationService.removeById(id);
    }

    @Override
    public PageInfo<OperationRecord> listOperationRecord(Page page, OperationRecord operationRecord) {
        return PageUtil.query(page, operationRecord, operationRecordService::query);
    }

}
