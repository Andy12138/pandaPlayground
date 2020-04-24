package com.zmg.panda.controller;

import com.zmg.panda.common.bean.ResultVO;
import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.service.IRedisPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andy
 */
@RestController
@RequestMapping("/api/panda")
public class pandaController {

    @Autowired
    private IRedisPublishService iPublishService;

    @GetMapping("/rootShow")
    public ResultVO rootShow() {
        return ResultVO.success("你也是一只熊猫！");
    }

    @GetMapping("/sendRedisMessage/{message}")
    public ResultVO<String> sendRedisMessage(@PathVariable String message) {
        iPublishService.sendMessage(RedisPojo.TOPIC_TITLE, message);
        return ResultVO.success();
    }
}
