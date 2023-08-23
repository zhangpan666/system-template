package com.gallery.manage.common.config.shiro;

import org.apache.shiro.web.filter.authc.UserFilter;

import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;


public class ShiroUserFilter extends UserFilter {


    @Override
    protected boolean preHandle(ServletRequest servletRequest, ServletResponse servletResponse) throws Exception {
        return super.preHandle(servletRequest, servletResponse);
    }


}
