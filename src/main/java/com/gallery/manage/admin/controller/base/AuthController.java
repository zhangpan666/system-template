package com.gallery.manage.admin.controller.base;

import com.gallery.manage.admin.service.AdminAuthService;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.util.OperationRecordUtil;
import com.light.core.model.CommonResult;
import com.light.core.utils.HttpUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.SecurityUtils;
import org.apache.shiro.subject.Subject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

@Controller
@RequestMapping("/gallery/manage/admin/auth")
@Slf4j
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class AuthController {

    @Autowired
    AdminAuthService adminAuthService;


    @RequestMapping("/toLogin")
    public ModelAndView toLogin(HttpServletRequest request) {
        String ipAddr = HttpUtil.getIpAddr(request);
        ModelAndView modelAndView = new ModelAndView("auth/login");
        modelAndView.addObject("clientIp", ipAddr);
        return modelAndView;
    }

    @RequestMapping("/login")
    @ResponseBody
    public CommonResult<Map<String, Object>> login(SysUser sysUser, HttpServletRequest request) {
        return adminAuthService.login(sysUser, request);
    }


    @RequestMapping("/logout")
    public ModelAndView logout() {
        Subject subject = SecurityUtils.getSubject();
        if (subject != null) {
            OperationRecordUtil.record(null, "后台用户", "退出");
            subject.logout();
        }
        return new ModelAndView("redirect:/gallery/manage/admin/auth/toLogin");
    }

    @RequestMapping("/403")
    public ModelAndView noAuth() {
        return new ModelAndView("error/403");
    }

    @RequestMapping("/error")
    public ModelAndView error() {
        return new ModelAndView("error/500");
    }

    @RequestMapping("/404")
    public ModelAndView notFound() {
        return new ModelAndView("404");
    }
}
