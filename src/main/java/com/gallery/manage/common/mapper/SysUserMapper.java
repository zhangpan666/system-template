package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.SysUser;
import com.gallery.manage.common.model.SysUser;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto generate
 * @since 2020-04-08
 */
public interface SysUserMapper extends BaseMapper<SysUser> {

    SysUser getUserByUsernameAndAccountType(@Param("username") String username, @Param("accountType") Byte accountType);

    List<SysUser>query(SysUser sysUser);

    int getCount(SysUser sysUser);

}
