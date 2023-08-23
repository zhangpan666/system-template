package com.gallery.manage.common.service;

import com.gallery.manage.common.model.Permission;
import com.baomidou.mybatisplus.extension.service.IService;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
public interface PermissionService extends IService<Permission> {

    List<Permission> getPermissionListByRoleId(Long roleId);

    int deleteByRoleId(Long roleId);

}
