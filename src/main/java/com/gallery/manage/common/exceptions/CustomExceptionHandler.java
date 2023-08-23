package com.gallery.manage.common.exceptions;


import com.light.core.model.CommonResult;
import com.light.core.utils.HttpUtil;
import org.apache.shiro.ShiroException;
import org.apache.shiro.web.servlet.ShiroHttpServletRequest;
import org.apache.shiro.web.servlet.ShiroHttpServletResponse;

//@ControllerAdvice
public class CustomExceptionHandler {


    public void handle(Exception e, ShiroHttpServletRequest request, ShiroHttpServletResponse response) throws Exception {
        e.printStackTrace();
        if (e instanceof ShiroException) {
            if (HttpUtil.isAjax(request)) {
                HttpUtil.responseWithJson(response, CommonResult.businessWrong("无操作权限"));
                return;
            }
            request.getRequestDispatcher("/gallery/manage/admin/auth/403").forward(request, response);
        } else {
            if (HttpUtil.isAjax(request)) {
                HttpUtil.responseWithJson(response, CommonResult.businessWrong("发生错误"));
                return;
            }
            request.getRequestDispatcher("/gallery/manage/admin/auth/error").forward(request, response);
        }


    }
}
