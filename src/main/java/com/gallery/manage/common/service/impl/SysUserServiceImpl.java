package com.gallery.manage.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallery.manage.common.mapper.SysUserMapper;
import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.service.SysUserService;
import com.gallery.manage.common.constants.ProjectConstant;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
@Service
public class SysUserServiceImpl extends ServiceImpl<SysUserMapper, SysUser> implements SysUserService {


    @Override
    public SysUser getAdminUserByUsername(String username) {
        return this.getUserByUsernameAndAccountType(username, ProjectConstant.UserType.ADMIN.getKey());
    }

    @Override
    public List<SysUser> query(SysUser sysUser) {
        return baseMapper.query(sysUser);
    }

    @Override
    public SysUser getUserByUsernameAndAccountType(String username, Byte accountType) {
        return baseMapper.getUserByUsernameAndAccountType(username, accountType);
    }

    @Override
    public Map<Long, String> getNicknameMap(List<Long> idList) {
        Map<Long, String> nicknameMap = new HashMap<>();
        if (CollectionUtils.isEmpty(idList)) {
            return nicknameMap;
        }
        List<SysUser> baseUserList = (List<SysUser>) listByIds(idList);
        if (!CollectionUtils.isEmpty(baseUserList)) {
            baseUserList.forEach(a -> nicknameMap.put(a.getId(), a.getNickname()));
        }
        return nicknameMap;
    }

    @Override
    public int getCount(SysUser sysUser) {
        return baseMapper.getCount(sysUser);
    }
}
