package com.gallery.manage.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallery.manage.common.mapper.SysUserRoleMapper;
import com.gallery.manage.common.model.SysUserRole;
import com.gallery.manage.common.service.SysUserRoleService;
import com.github.pagehelper.PageInfo;
import com.light.core.model.Page;
import com.light.core.utils.PageUtil;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
@Service
public class SysUserRoleServiceImpl extends ServiceImpl<SysUserRoleMapper, SysUserRole> implements SysUserRoleService {

    @Override
    public SysUserRole getUserRoleDetailByUserId(Long userId) {
        return baseMapper.getUserRoleDetailByUserId(userId);
    }

    @Override
    public PageInfo<SysUserRole> listUserRole(SysUserRole userRole, Page page) {
        return PageUtil.query(page, userRole, this::query);
    }

    @Override
    public List<SysUserRole> query(SysUserRole userRole) {
        return baseMapper.query(userRole);
    }
}
