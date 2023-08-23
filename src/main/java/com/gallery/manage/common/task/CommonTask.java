package com.gallery.manage.common.task;

import com.gallery.manage.common.util.ConfigurableVarUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class CommonTask {


    @Scheduled(cron = "0 */1 * * * ?")
    public void refreshConfigVar() {
        log.info("刷新系统常量");
        ConfigurableVarUtil.init();
    }
}
