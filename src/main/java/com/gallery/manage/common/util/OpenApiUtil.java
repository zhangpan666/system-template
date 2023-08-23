package com.gallery.manage.common.util;

import org.springframework.util.StringUtils;

import java.util.Map;

public class OpenApiUtil {


    public static String getSignString(Map<String, Object> param) {
        StringBuilder stringBuilder = new StringBuilder();
        param.forEach((key, value) -> {
            if (!StringUtils.isEmpty(key) && value != null) {
                stringBuilder.append(key).append("=").append(value).append("&");
            }
        });
        String string = stringBuilder.append(ConfigurableVarUtil.LOTTERY_SERVER_API_KEY()).toString();
        return MD5Util.MD5Encode(string, "UTF-8").toLowerCase();
    }




}
