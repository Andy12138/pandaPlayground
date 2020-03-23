package com.zmg20200111.panda.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

@Slf4j
@Component
@ServerEndpoint(value = "/webSocket/{userName}")
public class ZmgWebSocket {

    private static int onlineCount = 0;

    private static Set<ZmgWebSocket> webSocketSet = new HashSet<>();

    private Session session;

    private String userName;

    private static Map<String, ZmgWebSocket> webSocketMap = new HashMap<>();

    /**
     * 连接成功调用的方法
     * @param session
     * @param userName
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("userName") String userName) {
        this.session = session;
        this.userName = userName;
        webSocketSet.add(this);
        if (webSocketMap.containsKey(userName)) {
            webSocketMap.remove(userName);
        } else {
            this.addOnlineCount();
        }
        webSocketMap.put(userName, this);
        log.info("用户：{} 加入",userName);
        log.info("当前在线人数为：{}", getOnlineCount());
        try {
            sendMessage("欢迎" + userName + "加入");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        webSocketSet.remove(this); // 从set中删除
        subOnlineCount(); // 在线数减1
        log.info("有一连接关闭！当前在线人数为{}", getOnlineCount());
    }

    /**
     * 发生错误时调用
     *
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.info("发生错误");
        error.printStackTrace();
    }


    /**
     * 收到客户端消息后调用的方法
     * @param session
     * @param message
     */
    @OnMessage
    public void onMessage(Session session, String message) {
        message = "来自" + userName + "的消息: " + message;
        log.info("消息为[{}]", message);
        // 群发消息
        for (ZmgWebSocket webSocket : webSocketSet) {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                log.error("异常错误：[{}]", e.getMessage(), e);
            }
        }
    }

    /**
     * 群发自定义消息
     * @param message
     */
    public static void sendInfo(String message) {
        for (ZmgWebSocket webSocket : webSocketSet) {
            try {
                webSocket.sendMessage(message);
            } catch (IOException e) {
                log.error("异常错误：[{}]", e.getMessage(), e);
                continue;
            }
        }
    }

    /**
     * 发送自定义消息
     * 按userName发送
     * @param message
     * @param userName
     * @throws IOException
     */
    public static void sendInfo(String message, @PathParam("userName") String userName) throws IOException {
        log.info("发送消息到：" + userName + "，报文：" + message);
        if (webSocketMap.containsKey(userName)) {
            webSocketMap.get(userName).sendMessage(message);
        } else {
            log.error("用户" + userName + ",不在线！");
        }
    }

    /**
     * 发送消息
     * @param message
     */
    private void sendMessage(String message) throws IOException {
        this.session.getBasicRemote().sendText(message);
    }


    /**
     * 获取在线人数
     * @return
     */
    public static synchronized int getOnlineCount() {
        return onlineCount;
    }

    public static synchronized void addOnlineCount() {
        ZmgWebSocket.onlineCount++;
    }

    public static synchronized void subOnlineCount() {
        ZmgWebSocket.onlineCount--;
    }
}
