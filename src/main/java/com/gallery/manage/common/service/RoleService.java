package com.gallery.manage.common.service;

import com.gallery.manage.common.model.Role;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
public interface RoleService extends IService<Role> {

    List<Role> query(Role role);

}
