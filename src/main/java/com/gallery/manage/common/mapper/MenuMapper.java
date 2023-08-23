package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.Menu;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
public interface MenuMapper extends BaseMapper<Menu> {


    Menu getMenu(Menu Menu);

}
