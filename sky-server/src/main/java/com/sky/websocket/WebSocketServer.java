package com.sky.websocket;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.PathVariable;

import javax.websocket.OnClose;
import javax.websocket.OnMessage;
import javax.websocket.OnOpen;
import javax.websocket.Session;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Component
@ServerEndpoint("/ws/{sid}")
@Slf4j
public class WebSocketServer {
    //存放会话对象的的容器
    public static final Map<String, Session> sessionMap = new HashMap<>();

    //建立连接
    @OnOpen
    public void onOpen(Session session, @PathParam("sid") String sid) {
        log.info("\u001B[34m" + "客户端：{}建立连接" + "\u001B[0m", sid);
        sessionMap.put(sid, session);
    }

    //接收到消息
    @OnMessage
    public void onMessage(String message, @PathParam("sid") String sid) {
        log.info("\u001B[34m" + "接收到客户端{}的消息：{}" + "\u001B[0m", sid, message);
    }

    //关闭连接
    @OnClose
    public void onClose(@PathParam("sid") String sid) {
        log.info("\u001B[34m" + "客户端：{}关闭连接" + "\u001B[0m", sid);
        sessionMap.remove(sid);
    }

    //群发
    public void send2AllClient(String message) {
        Collection<Session> sessions = sessionMap.values();
        for (Session session : sessions) {
            try {
                session.getBasicRemote().sendText(message);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
