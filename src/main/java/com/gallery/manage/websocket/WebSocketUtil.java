package com.gallery.manage.websocket;

import com.alibaba.fastjson.JSON;
import com.gallery.manage.common.config.shiro.CustomAuthorizingRealm;
import com.gallery.manage.common.constants.ProjectConstant;
import com.gallery.manage.websocket.message.Message;
import com.light.config.util.ApplicationContextUtil;
import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisStringHashUtil;
import com.light.redis.util.RedisUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.util.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;
import java.util.stream.Collectors;

@Slf4j
public class WebSocketUtil {

    private static final ConcurrentHashMap<String, WebSocketServer> SESSION_MAP = new ConcurrentHashMap<>();
    private static final List<WebSocketServer> SESSION_LIST = new ArrayList<>();
    private static final ConcurrentHashMap<Long, WebSocketServer> SESSION_USER_ID_MAP = new ConcurrentHashMap<>();
    public static RedisInfo redisInfo = ProjectConstant.RedisKey.ONLINE_ADMIN_COUNT;


    public static void addClient(Long userId, String username, WebSocketServer webSocketServer) {
        SESSION_MAP.put(username, webSocketServer);
        SESSION_USER_ID_MAP.put(userId, webSocketServer);
        boolean add = SESSION_LIST.add(webSocketServer);
        if (add) {
            RedisInfo onlineAdmin = ProjectConstant.RedisKey.ONLINE_ADMIN;
            RedisStringHashUtil.put(onlineAdmin.getKeyPrefix(), username, username);
        }
    }

    public static void removeClient(Long userId, String username) {
        if (StringUtils.isEmpty(username)) {
            return;
        }
        WebSocketServer webSocketServer = SESSION_MAP.get(username);
        if (webSocketServer != null) {
            SESSION_MAP.remove(username);
            SESSION_USER_ID_MAP.remove(userId);
            boolean remove = SESSION_LIST.remove(webSocketServer);
            if (remove) {
                RedisInfo onlineAdmin = ProjectConstant.RedisKey.ONLINE_ADMIN;
                RedisStringHashUtil.delete(onlineAdmin.getKeyPrefix(), username);
            }
        }
    }

    public static int getOnlineCount() {
        return SESSION_MAP.size();
    }

    public static void incrementOnlineAdminCount() {
        RedisUtil.increment(redisInfo.getKeyPrefix());
    }

    public static void decrementOnlineAdminCount() {
        RedisUtil.decrement(redisInfo.getKeyPrefix());
    }

    public static WebSocketServer getClient(String username) {
        return SESSION_MAP.get(username);
    }

    public static WebSocketServer getClient(Long userId) {
        return SESSION_USER_ID_MAP.get(userId);
    }

    public static void sendMessage(Message message, String username) {
        WebSocketServer client = WebSocketUtil.getClient(username);
        sendMessage(message, client);
    }

    public static void sendMessage(Message message, Long userId) {
        WebSocketServer client = WebSocketUtil.getClient(userId);
        sendMessage(message, client);
    }


    private static void sendMessage(Message message, WebSocketServer client) {
        try {
            if (client != null) {
                String text = JSON.toJSONString(message);
                client.getSession().getBasicRemote().sendText(text);
            }
        } catch (Exception e) {
            e.printStackTrace();
            log.info(" WebSocket发送消息出错");
        }
    }

    public static void sendMessageToAll(Message message) {
        SESSION_LIST.forEach(webSocketServer -> sendMessage(message, webSocketServer));
    }


    public static void sendMessage(Message message) {
        try {
            if (message == null || message.getMessageType() == null) {
                return;
            }
            if (SESSION_MAP.size() == 0) {
                return;
            }
            Set<Map.Entry<String, WebSocketServer>> entries = SESSION_MAP.entrySet();
            byte messageType = message.getMessageType();
            if (messageType == ProjectConstant.MessageType.NEW_RECHARGE_COMPANY_ACCOUNT.getKey()) {
                entries = getEntries(entries, "rechargeRecord.company.select");
            } else if (messageType == ProjectConstant.MessageType.NEW_WITHDRAW_APPLY.getKey()) {
                entries = getEntries(entries, "withdraw.select");
            } else if (messageType == ProjectConstant.MessageType.RECHARGE_NOTICE.getKey()) {
                entries = getEntries(entries, "member.recharge.notice");
            } else if (messageType == ProjectConstant.MessageType.RECHARGE_BY_WITHDRAW_DIRECT.getKey()) {
                entries = getEntries(entries, "rechargeRecord.withdraw.direct.select");
            } else {
                return;
            }
            entries.forEach(stringWebSocketServerEntry -> {
                try {
                    sendMessage(message, stringWebSocketServerEntry.getValue());
                } catch (Exception e) {
                    e.printStackTrace();
                    log.info(" WebSocket发送消息出错");
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
            log.info(" WebSocket发送消息出错");
        }
    }

    private static Set<Map.Entry<String, WebSocketServer>> getEntries(Set<Map.Entry<String, WebSocketServer>> entries, String stringPermission) {
        CustomAuthorizingRealm customAuthorizingRealm = ApplicationContextUtil.getBean(CustomAuthorizingRealm.class);
        Set<Map.Entry<String, WebSocketServer>> entrySet = entries.stream().filter(stringWebSocketServerEntry -> customAuthorizingRealm.hasPermission(stringWebSocketServerEntry.getKey(), stringPermission)).collect(Collectors.toSet());
        return entrySet;
    }

}
