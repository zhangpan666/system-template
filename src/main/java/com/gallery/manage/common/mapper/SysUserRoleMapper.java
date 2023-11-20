package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.SysUserRole;
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
public interface SysUserRoleMapper extends BaseMapper<SysUserRole> {

    SysUserRole getUserRoleDetailByUserId(Long userId);

    List<SysUserRole>query(SysUserRole sysUserRole);
}
