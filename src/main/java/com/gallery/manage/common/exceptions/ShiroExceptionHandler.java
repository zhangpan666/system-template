package com.gallery.manage.common.exceptions;


import com.light.core.model.CommonResult;
import com.light.core.utils.HttpUtil;
import org.apache.shiro.ShiroException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class ShiroExceptionHandler {


    @ExceptionHandler(ShiroException.class)
    public void handle(Exception e, HttpServletRequest request, HttpServletResponse response) throws Exception {
        e.printStackTrace();
        if (HttpUtil.isAjax(request)) {
            HttpUtil.responseWithJson(response, CommonResult.businessWrong("无操作权限"));
            return;
        }
        request.getRequestDispatcher("/gallery/manage/admin/auth/403").forward(request, response);
    }
}
