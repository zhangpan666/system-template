package com.gallery.manage.admin.controller.base;

import com.gallery.manage.admin.service.AdminIndexService;
import com.gallery.manage.admin.pojo.MenuVO;
import com.gallery.manage.common.config.token.AdminLoginInfo;
import com.gallery.manage.common.util.LoginUtil;
import com.gallery.manage.common.util.SystemUtil;
import com.light.core.model.CommonResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import java.util.List;
import java.util.Map;

@Controller
@RequestMapping("/gallery/manage/admin")
@ConditionalOnProperty(value = "manage.enable", havingValue = "true")
public class IndexController extends BaseController {

    @Autowired
    AdminIndexService adminIndexService;


    @RequestMapping("/index")
    public ModelAndView index() {
        AdminLoginInfo adminLoginInfo = LoginUtil.getAdminLoginInfo();
        ModelAndView modelAndView = new ModelAndView("admin/index");
        modelAndView.addObject("loginInfo", adminLoginInfo);
        String websocketUrl=new StringBuilder().append(SystemUtil.getWebsocketUrl())
                .append(adminLoginInfo.getSessionId()).append("/").append(adminLoginInfo.getUserId())
                .append("/").append(adminLoginInfo.getUsername()).toString();
        modelAndView.addObject("websocketUrl",websocketUrl);
        return modelAndView;
    }


    @ResponseBody
    @RequestMapping("/init/menus")
    public CommonResult<List<MenuVO>> getMenuList() {
        List<MenuVO> menuList = adminIndexService.getMenuList();
        return CommonResult.success(menuList);
    }


    @RequestMapping(value = "/index/stat", method = {RequestMethod.GET,RequestMethod.POST})
    @ResponseBody
    public CommonResult stat() {
        Map<String, Object> stat = adminIndexService.stat();
        return CommonResult.success(stat);
    }



}
