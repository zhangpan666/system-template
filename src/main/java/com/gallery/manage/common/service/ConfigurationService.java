package com.gallery.manage.common.service;

import com.baomidou.mybatisplus.extension.service.IService;
import com.gallery.manage.common.model.Configuration;

import java.util.List;

/**
 * <p>
 * 服务类
 * </p>
 *
 * @author auto generate
 * @since 2020-06-19
 */
public interface ConfigurationService extends IService<Configuration> {

    List<Configuration> query(Configuration configuration);

    String getAdminAllowedIp();

    Configuration getByCode(String code);
}
