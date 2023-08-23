package com.gallery.manage.common.filter;

import com.gallery.manage.common.util.LoginUtil;
import lombok.extern.slf4j.Slf4j;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;

import javax.servlet.*;
import java.io.IOException;
import java.util.UUID;

@Slf4j
public class SignFilter implements Filter {


    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        log.info("SignFilter：{}", filterConfig);
    }

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) throws IOException, ServletException {
        ShiroHttpServletRequest request = (ShiroHttpServletRequest) servletRequest;
        try {
            chain.doFilter(servletRequest, servletResponse);
        } finally {
            LoginUtil.remove();
        }
    }


    @Override
    public void destroy() {

    }


    public static void main(String[] args) {
        System.out.println(UUID.randomUUID().toString().replaceAll("-", "").toUpperCase());
    }
}
