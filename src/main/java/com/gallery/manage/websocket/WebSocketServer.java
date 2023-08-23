package com.gallery.manage.websocket;

import com.light.redis.model.RedisInfo;
import com.light.redis.util.RedisUtil;
import com.light.shiro.constants.ShiroConstant;
import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;

@ServerEndpoint("/websocket/{sessionId}/{userId}/{username}")
@ConditionalOnProperty(value = "websocket.enable", havingValue = "true")
@Component
@Slf4j
@Data
public class WebSocketServer {


    private Session session;

    private Long userId;

    private String username;

    private String sessionId;


    @OnOpen
    public void onOpen(Session session, @PathParam("sessionId") String sessionId, @PathParam("userId") Long userId, @PathParam("username") String username) {
        this.session = session;
        this.userId = userId;
        this.username = username;
        this.sessionId = sessionId;
        RedisInfo redisInfo = ShiroConstant.RedisKey.USER_SESSION_CACHE;
        org.apache.shiro.session.Session shiroSession = RedisUtil.get(redisInfo.getKey(sessionId), redisInfo.getDbIndex());
        if (shiroSession == null) {
            try {
                CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.CANNOT_ACCEPT, "非法访问！");
                this.session.close(closeReason);
            } catch (IOException e) {
                e.printStackTrace();
                log.info("WebSocket关闭发生错误！用户ID：{}，用户名：{}，异常信息：{}", this.userId, this.username, e);
            }
            return;
        }
        WebSocketUtil.addClient(userId, username, this);
        log.info("WebSocket收到新连接！用户ID：{}，用户名：{}，当前在线人数：{}", this.userId, this.username, WebSocketUtil.getOnlineCount());
    }

    @OnClose
    public void onClose() {
        if (StringUtils.isEmpty(this.username)) {
            return;
        }
        WebSocketUtil.removeClient(this.userId, this.username);
        log.info("WebSocket连接关闭！用户ID：{}，用户名：{}，当前在线人数：{}", this.userId, this.username, WebSocketUtil.getOnlineCount());
    }

    @OnMessage
    public void onMessage(String message) {
        log.info("WebSocket收到消息！用户ID：{}，用户名：{}，消息内容：{}", this.userId, this.username, message);
    }

    @OnError
    public void onError(Throwable throwable) {
        log.info("WebSocket连接发生错误！用户ID：{}，用户名：{}，异常信息：{}", this.userId, this.username, throwable);
        CloseReason closeReason = new CloseReason(CloseReason.CloseCodes.CLOSED_ABNORMALLY, "WebSocket连接发生错误");
        try {
            this.session.close(closeReason);
        } catch (IOException e) {
            e.printStackTrace();
            log.info("WebSocket关闭发生错误！用户ID：{}，用户名：{}，异常信息：{}", this.userId, this.username, e);
        }
    }

}
