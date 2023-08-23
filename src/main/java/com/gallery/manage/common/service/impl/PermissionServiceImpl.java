package com.gallery.manage.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallery.manage.common.mapper.PermissionMapper;
import com.gallery.manage.common.model.Permission;
import com.gallery.manage.common.service.PermissionService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-09
 */
@Service
public class PermissionServiceImpl extends ServiceImpl<PermissionMapper, Permission> implements PermissionService {

    @Override
    public List<Permission> getPermissionListByRoleId(Long roleId) {
        return baseMapper.getPermissionListByRoleId(roleId);
    }

    @Override
    public int deleteByRoleId(Long roleId) {
        return baseMapper.deleteByRoleId(roleId);
    }
}
