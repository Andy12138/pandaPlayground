package com.zmg.panda.controller.websocket;

import com.zmg.panda.db.VisualDB;
import com.zmg.panda.manage.bean.User;
import com.zmg.panda.service.AuthUserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AnonymousAuthenticationToken;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping("/api/jumpPage")
public class WebSocketController {

    @Autowired
    private AuthUserDetailsService authUserDetailsService;

    // 跳转stomp test 页面
    @GetMapping(value = "/stompSocket")
    public String toStompWebSocket(Model model, @RequestParam String username, @RequestParam String token){
//        String token = VisualDB.TOKEN_DB.get(username);
        // 这里封装一个登录的用户组参数，模拟进入通讯后的简单初始化
        model.addAttribute("groupId","user_groupId");
        model.addAttribute("session_id", username);
        model.addAttribute("username", username);
        model.addAttribute("token",token);
        System.out.println("跳转：" + username);
        return "/test/springWebSocketStomp.html";
    }

    // 跳转stomp test 页面
    @GetMapping(value = "/authUser")
    public String toStompWebSocket(){
//        String token = VisualDB.TOKEN_DB.get(username);
        // 这里封装一个登录的用户组参数，模拟进入通讯后的简单初始化
//        model.addAttribute("groupId","pandaGroup");
//        model.addAttribute("username", username);
//        model.addAttribute("token",token);
//        System.out.println("跳转：" + username);
//        return "/test/springWebSocketStomp.html";
        return "认证成功！";
    }
}
