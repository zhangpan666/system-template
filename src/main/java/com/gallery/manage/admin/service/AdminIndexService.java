package com.gallery.manage.admin.service;

import com.gallery.manage.admin.pojo.MenuVO;

import java.util.List;
import java.util.Map;

public interface AdminIndexService {


    List<MenuVO>getMenuList();

    Map<String, Object> stat();
}
