package com.gallery.manage.admin.service;

import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUserRole;
import com.github.pagehelper.PageInfo;
import com.light.core.model.CommonResult;
import com.light.core.model.Page;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public interface AdminUserService {

    PageInfo<SysUser> listUser(Page page, SysUser sysUser);

    SysUser getUserDetail(Long id);

    CommonResult updateUser(SysUser sysUser);

    CommonResult saveUser(SysUser sysUser, SysUserRole userRole);

    CommonResult removeBgUser(Long id);

    boolean authUser(SysUserRole userRole);

    CommonResult changePassword(String oldPassword, String newPassword);

    void generateGoogleAuthQrCode(String username, HttpServletRequest request, HttpServletResponse response) throws RuntimeException;
}
