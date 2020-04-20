package com.zmg.panda.manage.websocket;

import com.zmg.panda.manage.bean.WebsocketUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.util.StringUtils;
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
        String username = (String)httpSession.getAttribute("username");
        if(StringUtils.isEmpty(username)){
            log.error("未登录系统，禁止登录websocket!");
            return null;
        }
        log.info(" MyDefaultHandshakeHandler login = " + username);
        return new WebsocketUserAuthentication(username);
    }

    private HttpSession getSession(ServerHttpRequest request) {
        if (request instanceof ServletServerHttpRequest) {
            ServletServerHttpRequest serverRequest = (ServletServerHttpRequest) request;
            return serverRequest.getServletRequest().getSession(false);
        }
        return null;
    }

}