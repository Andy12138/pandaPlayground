package com.zmg.panda.controller.websocket;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class WebJumpToWebsocketController {

    @RequestMapping("/api/root/toChat")
    public String jumpToPage() {
        return "/websocket/websocket.html";
    }
}
