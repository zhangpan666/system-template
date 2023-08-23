package com.gallery.manage.admin.controller.base;

import com.gallery.manage.common.util.SystemUtil;
import com.github.pagehelper.PageInfo;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;

public class BaseController {

    /**
     * @param request
     * @param viewName
     * @param pageInfo
     * @param condition
     * @return
     */

    public ModelAndView getCommonModelAndView(HttpServletRequest request, String viewName, PageInfo pageInfo, Object condition) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("contextPath", SystemUtil.getBaseRequestUrl());
        modelAndView.addObject("page", pageInfo);
        modelAndView.addObject("condition", condition);
        modelAndView.addObject("requestUrl", request.getRequestURI());
        return modelAndView;
    }

    /**
     * @param request
     * @param viewName
     * @return
     */
    public ModelAndView getCommonModelAndView(HttpServletRequest request, String viewName) {
        ModelAndView modelAndView = new ModelAndView(viewName);
        modelAndView.addObject("contextPath", SystemUtil.getBaseRequestUrl());
        modelAndView.addObject("requestUrl", request.getRequestURI());
        return modelAndView;
    }
}
