package com.gallery.manage.common.starter;

import com.gallery.manage.common.service.CacheService;
import com.gallery.manage.common.util.*;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

@Component
@Slf4j
public class StartRunner implements ApplicationListener<ContextRefreshedEvent> {

    @Resource
    MenuUtil menuUtil;

    @Autowired
    CacheService cacheService;

    @Override
    public void onApplicationEvent(ContextRefreshedEvent event){
        if (SystemUtil.isManage()) {
            log.info("======初始化菜单开始=====");
            new Thread(menuUtil::initMenu).start();
            new Thread(cacheService::clearSysUserAuthorization).start();
            log.info("======初始化菜单结束=====");
        }
        new Thread(ConfigurableVarUtil::init).start();
        log.info("======系统启动完成======");
    }
}
