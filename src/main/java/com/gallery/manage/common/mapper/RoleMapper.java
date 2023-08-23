package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.Role;
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
public interface RoleMapper extends BaseMapper<Role> {

    List<Role>query(Role role);

}
