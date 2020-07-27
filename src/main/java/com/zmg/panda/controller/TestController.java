package com.zmg.panda.controller;

import io.swagger.annotations.Api;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Api(tags = "panda接口")
@RestController
public class TestController {

    @GetMapping("/")
    public String test() {
        return "websocket.html";
    }

}
