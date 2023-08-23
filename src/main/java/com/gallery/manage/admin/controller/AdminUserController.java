package com.gallery.manage.admin.controller;

import com.gallery.manage.admin.controller.base.BaseController;
import com.gallery.manage.admin.service.AdminUserService;
import com.gallery.manage.common.aop.annotations.Operation;
import com.gallery.manage.common.model.Role;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.gallery.manage.common.service.RoleService;
import com.gallery.manage.common.service.SysUserRoleService;
import com.gallery.manage.common.util.SystemUtil;
import com.gallery.manage.common.constants.ProjectConstant;
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
import javax.servlet.http.HttpServletResponse;
import java.util.List;

@Controller
@RequestMapping("/gallery/manage/admin/sysUser")
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class AdminUserController extends BaseController {

    @Autowired
    AdminUserService adminUserService;

    @Autowired
    RoleService roleService;

    @Autowired
    SysUserRoleService sysUserRoleService;


    @RequestMapping("/list")
    @RequiresPermissions({"sysUser.select"})
    public ModelAndView userList(HttpServletRequest request, Page page, SysUser sysUser) {
        sysUser.setAccountType(ProjectConstant.UserType.ADMIN.getKey());
        PageInfo<SysUser> userPageInfo = adminUserService.listUser(page, sysUser);
        return this.getCommonModelAndView(request, "sysuser/sys_user_list", userPageInfo, sysUser);
    }

    @RequestMapping("getUpdatePage/{id}")
    @RequiresPermissions({"sysUser.update"})
    public ModelAndView getBgUserUpdatePage(@PathVariable("id") Long id, HttpServletRequest request) {
        SysUser userDetail = adminUserService.getUserDetail(id);
        ModelAndView modelAndView = this.getCommonModelAndView(request, "sysuser/sys_user_update");
        modelAndView.addObject("sysUser", userDetail);
        return modelAndView;
    }


    @RequestMapping("update")
    @ResponseBody
    @RequiresPermissions({"sysUser.update"})
    @Operation(module = "后台用户", description = "修改")
    @NeedAdminLogin
    public CommonResult updateUser(Long id,SysUser sysUser) {
        return adminUserService.updateUser(sysUser);

    }

    @RequestMapping("getAddPage")
    @RequiresPermissions({"sysUser.add"})
    public ModelAndView getSysUserAddPage(HttpServletRequest request) {
        List<Role> roleList = roleService.list();
        ModelAndView modelAndView = this.getCommonModelAndView(request, "sysuser/sys_user_add");
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("adminRole", SystemUtil.getAdminRole());
        return modelAndView;
    }

    @RequestMapping("save")
    @ResponseBody
    @RequiresPermissions({"sysUser.add"})
    @Operation(module = "后台用户", description = "添加")
    @NeedAdminLogin
    public CommonResult saveUser(SysUser sysUser, SysUserRole userRole) {
        return adminUserService.saveUser(sysUser, userRole);
    }

    @RequestMapping("delete/{id}")
    @ResponseBody
    @RequiresPermissions({"sysUser.delete"})
    @Operation(module = "后台用户", description = "删除")
    @NeedAdminLogin
    public CommonResult saveUser(@PathVariable("id") Long id) {
        return adminUserService.removeBgUser(id);
    }

    @RequestMapping("getAuthPage/{id}")
    @RequiresPermissions({"sysUser.role.update"})
    public ModelAndView getUserAuthPage(@PathVariable("id") Long id, HttpServletRequest request) {
        SysUser userDetail = adminUserService.getUserDetail(id);
        List<Role> roleList = roleService.list();
        SysUserRole userRole = sysUserRoleService.getById(id);
        if (userRole != null) {
            userDetail.setRoleId(userRole.getRoleId());
        }
        ModelAndView modelAndView = this.getCommonModelAndView(request, "sysuser/sys_user_auth");
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("sysUser", userDetail);
        modelAndView.addObject("adminRole", SystemUtil.getAdminRole());
        return modelAndView;
    }


    @RequestMapping("/role/list")
    @RequiresPermissions({"sysUser.role.select"})
    public ModelAndView listUserRole(SysUserRole userRole, Page page, HttpServletRequest request) {
        PageInfo<SysUserRole> userRoleList = sysUserRoleService.listUserRole(userRole, page);
        ModelAndView modelAndView = this.getCommonModelAndView(request, "sysuser/sys_user_role_list", userRoleList, userRole);
        List<Role> roleList = roleService.list();
        modelAndView.addObject("roleList", roleList);
        return modelAndView;
    }


    @RequestMapping("/role/getUpdatePage/{id}")
    @RequiresPermissions({"sysUser.role.update"})
    public ModelAndView getUserRoleUpdatePage(@PathVariable("id") Long id, HttpServletRequest request) {
        SysUserRole userRole = sysUserRoleService.getById(id);
        List<Role> roleList = roleService.list();
        ModelAndView modelAndView = this.getCommonModelAndView(request, "sysuser/sys_user_role_update");
        modelAndView.addObject("userRole", userRole);
        modelAndView.addObject("roleList", roleList);
        modelAndView.addObject("adminRole", SystemUtil.getAdminRole());
        return modelAndView;
    }

    @RequestMapping("/auth")
    @ResponseBody
    @RequiresPermissions({"sysUser.role.update"})
    @Operation(module = "用户角色", description = "修改")
    @NeedAdminLogin
    public CommonResult authUser(Long id,SysUserRole userRole) {
        Long roleId = userRole.getRoleId();
        Role role = roleService.getById(roleId);
        if (SystemUtil.getAdminRole().equals(role.getRoleCode())) {
            return CommonResult.businessWrong("您无权进行此操作");
        }
        boolean result = adminUserService.authUser(userRole);
        if (result) {
            return CommonResult.success();
        }
        return CommonResult.businessWrong();
    }

    @RequestMapping("/getChangePasswordPage")
    public ModelAndView getChangePasswordPage(HttpServletRequest request) {
        return this.getCommonModelAndView(request, "sysuser/change_password");
    }

    @RequestMapping("/changePassword")
    @ResponseBody
    @NeedAdminLogin
    public CommonResult changePassword(String oldPassword, String newPassword) {
        return adminUserService.changePassword(oldPassword, newPassword);
    }

    @RequestMapping("/googleAuthQrCode")
    @RequiresPermissions({"sysUser.verifyCode"})
    @Operation(module = "后台用户", description = "获取登录验证码")
    public void googleAuthQrCode(String username,HttpServletRequest request, HttpServletResponse response) {
        adminUserService.generateGoogleAuthQrCode(username,request, response);
    }

}
