package com.gallery.manage.common.service.impl;

import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.gallery.manage.common.mapper.ConfigurationMapper;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.service.ConfigurationService;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * <p>
 * 服务实现类
 * </p>
 *
 * @author auto generate
 * @since 2020-06-19
 */
@Service
public class ConfigurationServiceImpl extends ServiceImpl<ConfigurationMapper, Configuration> implements ConfigurationService {

    @Override
    public List<Configuration> query(Configuration configuration) {
        return baseMapper.query(configuration);
    }

    @Override
    public String getAdminAllowedIp() {
        Configuration configuration = this.getByCode("ADMIN_ALLOWED_IP");
       if (configuration==null){
           return null;
       }
        return configuration.getValue();
    }

    @Override
    public Configuration getByCode(String code) {
        return baseMapper.getByCode(code);
    }


}
