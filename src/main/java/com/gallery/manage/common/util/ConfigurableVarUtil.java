package com.gallery.manage.common.util;

import com.alibaba.fastjson.JSONObject;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.common.model.Configuration;
import com.gallery.manage.common.service.ConfigurationService;
import com.light.config.util.ApplicationContextUtil;
import org.springframework.util.CollectionUtils;

import java.math.BigDecimal;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConfigurableVarUtil {

    private static final ConcurrentMap<String, String> varMap = new ConcurrentHashMap<>();

    public static int SMS_LIMIT_PER_DAY() {
        return get("SMS_LIMIT_PER_DAY", Integer.class, 5);
    }

    public static int SAVE_IP_USER_COUNT() {
        return get("SAVE_IP_USER_COUNT", Integer.class, 5);
    }

    public static String NOT_ALLOWED_REGISTER_MODELS() {
        return get("NOT_ALLOWED_REGISTER_MODELS", String.class, "");
    }

    public static Byte CG_PAY_STATUS() {
        return get("CG_PAY_STATUS", Byte.class, ProjectConstant.COMMON_STATUS_AVAILABLE);
    }

    public static String LOTTERY_SERVER_API_ID() {
        return get("LOTTERY_SERVER_API_ID", String.class);
    }

    public static String LOTTERY_SERVER_API_KEY() {
        return get("LOTTERY_SERVER_API_KEY", String.class);
    }

    public static String ONLINE_CUSTOMER_SERVICE() {
        return get("ONLINE_CUSTOMER_SERVICE", String.class);
    }

    public static Integer MAX_USER_TRY_LOGIN_COUNT_DAY() {
        return get("MAX_USER_TRY_LOGIN_COUNT_DAY", Integer.class, 3);
    }

    public static long USER_COMMENT_INTERVAL_IN_SECOND() {
        return get("USER_COMMENT_INTERVAL_IN_SECOND", Long.class, 10L);
    }

    public static long SAME_COMMENT_TIME_IN_MINUTE() {
        return get("SAME_COMMENT_TIME_IN_MINUTE", Long.class, 60L);
    }

    public static int SAME_COMMENT_COUNT() {
        return get("SAME_COMMENT_COUNT", Integer.class, 5);
    }


    public static JSONObject getTotal() {
        if (CollectionUtils.isEmpty(varMap)) {
            init();
        }
        return JSONObject.parseObject(JSONObject.toJSONString(varMap));
    }


    public static synchronized void init() {
        ConfigurationService configurableVarService = ApplicationContextUtil.getBean(ConfigurationService.class);
        if (configurableVarService == null) {
            return;
        }
        List<Configuration> list = configurableVarService.list();
        list.forEach(var -> {
            varMap.put(var.getCode(), var.getValue());
        });
    }

    public static <T> T get(String key, Class<T> clazz, T defaultValue) {
        T val = get(key, clazz);
        if (val == null) {
            return defaultValue;
        }
        return val;
    }

    public static <T> T get(String key, Class<T> clazz) {
        String val = varMap.get(key);
        if (val == null) {
            return null;
        }
        if (clazz.equals(Integer.class)) {
            return (T) Integer.valueOf(val);
        } else if (clazz.equals(Long.class)) {
            return (T) Long.valueOf(val);
        } else if (clazz.equals(Double.class)) {
            return (T) Double.valueOf(val);
        } else if (clazz.equals(Float.class)) {
            return (T) Float.valueOf(val);
        } else if (clazz.equals(Byte.class)) {
            return (T) Byte.valueOf(val);
        } else if (clazz.equals(String.class)) {
            return (T) val;
        } else if (clazz.equals(Boolean.class)) {
            return (T) Boolean.valueOf(val);
        } else if (clazz.equals(BigDecimal.class)) {
            return (T) new BigDecimal(val);
        } else if (clazz.equals(Date.class)) {
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");

            try {
                return (T) sdf.parse(val);
            } catch (ParseException e) {
                e.printStackTrace();
            }
            return null;
        }
        throw new RuntimeException("该类型不存在！！！");
    }

}
