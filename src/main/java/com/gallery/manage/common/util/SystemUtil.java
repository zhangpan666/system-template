package com.gallery.manage.common.util;

import com.light.config.util.PropertiesUtil;
import com.light.core.utils.RegularExpUtil;
import com.light.core.utils.StringUtil;
import org.springframework.util.StringUtils;

import java.net.URLEncoder;

public class SystemUtil {

    public static String applicationProperties() {
        return PropertiesUtil.getProperty("application.properties", "application.properties");
    }

    public static boolean isTest() {
        return PropertiesUtil.getProperty("isTest", Boolean.class, true);
    }

    public static boolean isManage() {
        return PropertiesUtil.getProperty("manage.enable", Boolean.class, false);
    }

    public static boolean enableTask() {
        return PropertiesUtil.getProperty("task.enable", Boolean.class, false);
    }

    public static boolean isAdminRole(String roleCode) {
        if (StringUtils.isEmpty(roleCode)) {
            return false;
        }
        if (getAdminRole().equals(roleCode)) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isAdmin(String username) {
        if (StringUtils.isEmpty(username)) {
            return false;
        }
        if (getAdminUsername().equals(username)) {
            return true;
        } else {
            return false;
        }
    }

    public static String getAdminRole() {
        return PropertiesUtil.getProperty("admin.role", "admin");

    }

    public static String getAdminUsername() {
        return PropertiesUtil.getProperty("admin.username", "admin");

    }

    public static String getBaseRequestUrl() {
        return PropertiesUtil.getProperty("application.admin.base.request.url", "/gallery/manage/admin");
    }

    public static String getVerifyCode() {
        return PropertiesUtil.getProperty("admin.login.verify.code", String.class, "admin$$123456$$");
    }

    public static String fillFileHost(String path) {
        if (StringUtils.isEmpty(path) || path.startsWith("http://") || path.startsWith("https://")) {
            return replaceChinese(path);
        }
        return replaceChinese(defaultFileHost() + path);
    }

    public static String fillFileHost(String path, String defaultPath) {
        if (StringUtils.isEmpty(path) && !StringUtils.isEmpty(defaultPath)) {
            return fillFileHost(defaultPath);
        }
        if (StringUtils.isEmpty(path) || path.startsWith("http://") || path.startsWith("https://")) {
            return replaceChinese(path);
        }
        return replaceChinese(defaultFileHost() + path);
    }

    public static String replaceChinese(String url) {
        if (StringUtils.isEmpty(url)) {
            return url;
        }
        StringBuilder stringBuilder=new StringBuilder();
        //遍历字符串
        try {
            for (int i = 0; i < url.length(); i++) {
                char charAt = url.charAt(i);
                //只对汉字处理
                if (RegularExpUtil.isChineseChar(charAt)) {
                    String encode = URLEncoder.encode(String.valueOf(charAt), "UTF-8");
                    stringBuilder.append(encode);
                } else {
                    stringBuilder.append(charAt);
                }
            }
            return stringBuilder.toString();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return url;
    }


    public static String getFilePathPrefix()  {
        return PropertiesUtil.getProperty("file.path.prefix", "");
    }

    public static String defaultFileHost() {
        return PropertiesUtil.getProperty("system.host.file", "https://cpdlwj.com/files");
    }

    public static String getFileRootDir() {
        return PropertiesUtil.getProperty("file.root.dir", "/home/data/files");
    }


    public static String getWebsocketUrl() {
        return PropertiesUtil.getProperty("websocket.url", "ws://127.0.0.1:9950/websocket/");
    }

    public static String defaultTopic() {
        return PropertiesUtil.getProperty("kafka.lottery.topic", "lottery_topic");
    }
}
