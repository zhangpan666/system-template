package com.gallery.manage.common.util.threadpool;

import com.light.config.util.PropertiesUtil;
import com.light.core.utils.ThreadPoolUtils;
import org.springframework.context.annotation.DependsOn;
import org.springframework.stereotype.Component;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.TimeUnit;

@Component
@DependsOn(value = "applicationContextUtil")
public class ThreadPoolUtil {
    public volatile static ExecutorService executorService = ThreadPoolUtils.getThreadPool(PropertiesUtil.getProperty("thread.pool.core.size",
            Integer.class, 1), PropertiesUtil.getProperty("thread.pool.max.size", Integer.class, 10), 2, TimeUnit.MINUTES, 1024);
}
