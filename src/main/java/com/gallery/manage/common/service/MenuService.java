package com.gallery.manage.common.service;

import com.gallery.manage.common.model.Menu;
import com.baomidou.mybatisplus.extension.service.IService;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
public interface MenuService extends IService<Menu> {

    Menu getMenu(Menu Menu);

}
