package com.gallery.manage.common.service.impl;

import com.gallery.manage.common.model.Role;
import com.gallery.manage.common.mapper.RoleMapper;
import com.gallery.manage.common.service.RoleService;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 *  服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
@Service
public class RoleServiceImpl extends ServiceImpl<RoleMapper, Role> implements RoleService {

    @Override
    public List<Role> query(Role role) {
        return baseMapper.query(role);
    }
}
