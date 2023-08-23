package com.gallery.manage.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallery.manage.common.mapper.MenuMapper;
import com.gallery.manage.common.model.Menu;
import com.gallery.manage.common.service.MenuService;
import org.springframework.stereotype.Service;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
@Service
public class MenuServiceImpl extends ServiceImpl<MenuMapper, Menu> implements MenuService {

    @Override
    public Menu getMenu(Menu Menu) {
        return baseMapper.getMenu(Menu);
    }

}
