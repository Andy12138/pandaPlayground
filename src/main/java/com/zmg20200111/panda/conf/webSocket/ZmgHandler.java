package com.zmg20200111.panda.conf.webSocket;

import org.springframework.web.socket.TextMessage;
import org.springframework.web.socket.WebSocketSession;
import org.springframework.web.socket.handler.TextWebSocketHandler;

//@Slf4j
public class ZmgHandler extends TextWebSocketHandler {

    public void handleTextMessage(WebSocketSession session, TextMessage message) {
        // todo
        System.out.println("怎么了，这是什么信息"+"["+message.toString()+"]");
//        log.info("怎么了，这是什么信息[{}],[{}]", session.toString(), message.toString());
    }
}
