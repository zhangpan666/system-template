package com.gallery.manage.common.mapper;

import com.gallery.manage.common.model.Configuration;
import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * <p>
 *  Mapper 接口
 * </p>
 *
 * @author auto generate
 * @since 2020-06-19
 */
public interface ConfigurationMapper extends BaseMapper<Configuration> {

    List<Configuration>query(Configuration configuration);

    Configuration getByCode(@Param("code") String code);

}
