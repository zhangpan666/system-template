package com.gallery.manage.admin.service;

import com.gallery.manage.common.model.SysUser;
import com.light.core.model.CommonResult;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

public interface AdminAuthService {
    CommonResult<Map<String, Object>> login(SysUser sysUser, HttpServletRequest request);
}
