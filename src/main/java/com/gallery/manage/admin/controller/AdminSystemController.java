package com.gallery.manage.admin.controller;

import com.gallery.manage.admin.controller.base.BaseController;
import com.gallery.manage.admin.pojo.AdminPermissionVO;
import com.gallery.manage.admin.service.AdminSystemService;
import com.gallery.manage.common.aop.annotations.Operation;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.model.OperationRecord;
import com.gallery.manage.common.model.Role;
import com.github.pagehelper.PageInfo;
import com.light.config.annotations.NeedAdminLogin;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

@Controller
@RequestMapping("/gallery/manage/admin/system")
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class AdminSystemController extends BaseController {

    @Autowired
    AdminSystemService adminSystemService;


    @RequestMapping("/role/list")
    @RequiresPermissions({"role.select"})
    public ModelAndView listRole(Page page, Role role, HttpServletRequest request) {
        PageInfo<Role> roleList = adminSystemService.listRole(page, role);
        ModelAndView modelAndView = this.getCommonModelAndView(request, "system/role/role_list", roleList, role);
        return modelAndView;
    }

    @RequestMapping("/role/getAddPage")
    @RequiresPermissions({"role.add"})
    public ModelAndView getRoleAddPage(HttpServletRequest request) {
        return this.getCommonModelAndView(request, "system/role/role_add");
    }

    @ResponseBody
    @RequestMapping("/role/add")
    @RequiresPermissions({"role.add"})
    @Operation(module = "角色", description = "添加")
    @NeedAdminLogin
    public CommonResult saveRole(Role role) {
        boolean saveRole = adminSystemService.saveRole(role);
        if (saveRole) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @RequestMapping("/role/getUpdatePage/{id}")
    @RequiresPermissions({"role.update"})
    public ModelAndView getRoleUpdatePage(@PathVariable("id") Long id, HttpServletRequest request) {
        Role roleDetail = adminSystemService.getRoleDetail(id);
        ModelAndView modelAndView = this.getCommonModelAndView(request, "system/role/role_update");
        modelAndView.addObject("roleDetail", roleDetail);
        return modelAndView;
    }

    @RequestMapping("/role/update")
    @ResponseBody
    @RequiresPermissions({"role.update"})
    @Operation(module = "角色", description = "修改")
    @NeedAdminLogin
    public CommonResult updateRole(Long id, Role role, HttpServletRequest request) {
        adminSystemService.updateRole(role);
        return CommonResult.success();
    }

    @ResponseBody
    @RequestMapping("/role/delete/{id}")
    @RequiresPermissions({"role.delete"})
    @Operation(module = "角色", description = "删除")
    @NeedAdminLogin
    public CommonResult deleteRole(@PathVariable("id") Long id) {
        boolean deleteRole = adminSystemService.deleteRole(id);
        if (deleteRole) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }


    @RequestMapping("/role/getPermissionDetailPage/{id}")
    @RequiresPermissions({"role.select"})
    public ModelAndView getRolePermissionDetailPage(@PathVariable("id") Long id, HttpServletRequest request) {
        ModelAndView modelAndView = this.getCommonModelAndView(request, "system/role/role_permission");
        return adminSystemService.getPermissionDetailPage(id, modelAndView);
    }


    @RequestMapping("/permission/save")
    @ResponseBody
    @RequiresPermissions({"role.add"})
    @Operation(module = "角色", description = "权限设置")
    @NeedAdminLogin
    public CommonResult savePermission(Long roleId, AdminPermissionVO adminPermissionVO, HttpServletRequest request) {
        return adminSystemService.savePermission(adminPermissionVO);
    }

    @RequestMapping("/configuration/list")
    @RequiresPermissions({"configuration.select"})
    public ModelAndView listConfiguration(Page page, Configuration configuration, HttpServletRequest request) {
        PageInfo<Configuration> configurationList = adminSystemService.listConfiguration(page, configuration);
        ModelAndView modelAndView = this.getCommonModelAndView(request, "system/configuration/configuration_list", configurationList, configuration);
        return modelAndView;
    }

    @RequestMapping("/configuration/getAddPage")
    @RequiresPermissions({"configuration.add"})
    public ModelAndView getConfigurationAddPage() {
        return new ModelAndView("system/configuration/configuration_add");
    }

    @RequestMapping("/configuration/add")
    @ResponseBody
    @Operation(module = "常量配置", description = "添加")
    @RequiresPermissions({"configuration.add"})
    @NeedAdminLogin
    public CommonResult addConfiguration(Configuration configuration, HttpServletRequest request) {
        return adminSystemService.addConfiguration(configuration);
    }


    @RequestMapping("/configuration/getUpdatePage/{id}")
    @RequiresPermissions({"configuration.update"})
    public ModelAndView getConfigurationUpdatePage(@PathVariable("id") Long id) {
        Configuration configuration = adminSystemService.getConfigurationById(id);
        ModelAndView modelAndView = new ModelAndView("system/configuration/configuration_update");
        modelAndView.addObject("configuration", configuration);
        return modelAndView;
    }


    @RequestMapping("/configuration/update")
    @ResponseBody
    @Operation(module = "常量配置", description = "修改")
    @RequiresPermissions({"configuration.update"})
    @NeedAdminLogin
    public CommonResult updateConfiguration(Long id, Configuration configuration, HttpServletRequest request) {
        return adminSystemService.updateConfigurationById(configuration);
    }

    @RequestMapping("/configuration/delete/{id}")
    @ResponseBody
    @Operation(module = "常量配置", description = "删除")
    @RequiresPermissions({"configuration.delete"})
    @NeedAdminLogin
    public CommonResult deleteConfiguration(@PathVariable("id") Long id, HttpServletRequest request) {
        boolean result = adminSystemService.deleteConfigurationById(id);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }


    @RequestMapping("/operationRecord/list")
    @RequiresPermissions({"operationRecord.select"})
    public ModelAndView listOperationRecord(Page page, OperationRecord operationRecord, HttpServletRequest request) {
        PageInfo<OperationRecord> operationRecordList = adminSystemService.listOperationRecord(page, operationRecord);
        return this.getCommonModelAndView(request, "system/operation/operation_record_list", operationRecordList, operationRecord);
    }



}
