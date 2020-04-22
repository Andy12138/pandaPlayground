package com.zmg.panda.controller.websocket;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpSession;

@RestController
@RequestMapping("/api/jumpPage")
public class WebSocketController {

    @GetMapping(value = "/authUser")
    public String toStompWebSocket(HttpSession session){
        session.setAttribute("god", "钟名桂");
        return session.getAttribute("username").toString();
    }
}
