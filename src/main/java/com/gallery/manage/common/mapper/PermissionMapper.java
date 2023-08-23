package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.Permission;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
public interface PermissionMapper extends BaseMapper<Permission> {

    List<Permission>getPermissionListByRoleId(Long roleId);

    int deleteByRoleId(@Param("roleId")Long roleId);

}
