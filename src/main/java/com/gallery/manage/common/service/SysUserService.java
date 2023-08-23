package com.gallery.manage.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gallery.manage.common.model.SysUser;

import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
public interface SysUserService extends IService<SysUser> {

    SysUser getAdminUserByUsername(String username);

    List<SysUser> query(SysUser sysUser);

    SysUser getUserByUsernameAndAccountType(String username, Byte accountType);

    Map<Long, String> getNicknameMap(List<Long> idList);

    int getCount(SysUser sysUser);
}
