package com.zmg.panda.manage.websocket;

import com.zmg.panda.manage.bean.WebsocketUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.web.socket.WebSocketHandler;
import org.springframework.web.socket.server.support.DefaultHandshakeHandler;

import javax.servlet.http.HttpSession;
import java.security.Principal;
import java.util.Map;

/**
 * @author Andy
 */
@Slf4j
public class MyPrincipalHandshakeHandler extends DefaultHandshakeHandler {

    @Override
    protected Principal determineUser(ServerHttpRequest request, WebSocketHandler wsHandler, Map<String, Object> attributes) {
        HttpSession httpSession = getSession(request);
        if (httpSession != null && httpSession.getAttribute("username") != null) {
            String username = String.valueOf(httpSession.getAttribute("username"));
            log.info(" MyDefaultHandshakeHandler login = " + username);
            return new WebsocketUserAuthentication(username);
        } else {
            log.error("未登录系统，禁止登录websocket!");
            return null;
        }
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }

}