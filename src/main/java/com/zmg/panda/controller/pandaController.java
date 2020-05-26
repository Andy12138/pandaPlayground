package com.zmg.panda.controller;

import com.zmg.panda.common.bean.OnlineUser;
import com.zmg.panda.common.bean.ResultVO;
import com.zmg.panda.common.bean.WsMessage;
import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.service.IRedisPublishService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

/**
 * @author Andy
 */
@Api(tags = "panda接口")
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

    @ApiOperation("测试一下新增呗1")
    @PostMapping("/addTest1")
    public ResultVO<OnlineUser> addTest1(@ApiParam("在线用户") @RequestBody OnlineUser onlineUser) {
        System.out.println("我在执行方法鸟~" + onlineUser.toString());
        return ResultVO.success(onlineUser);
    }

    @ApiOperation("测试一下新增呗2")
    @PostMapping("/addTest2")
    public ResultVO<OnlineUser> addTest2(@ApiParam("在线用户") @Validated WsMessage wsMessage) {
        System.out.println("我在执行方法鸟~" + wsMessage.toString());
        return ResultVO.success(wsMessage);
    }

    @ApiOperation("测试一下查询1")
    @GetMapping("/getTest1/{username}")
    public ResultVO<OnlineUser> getTest1(@PathVariable String username) {
        System.out.println("我在执行方法鸟~" + username);
        return ResultVO.success();
    }

    @ApiOperation("测试一下查询2")
    @GetMapping("/getTest2")
    public ResultVO<OnlineUser> getTest2(@RequestParam String username) {
        System.out.println("我在执行方法鸟~" + username);
        return ResultVO.success();
    }

    @ApiOperation("测试一下查询3")
    @GetMapping("/getTest3/{username}")
    public ResultVO<OnlineUser> getTest3(@PathVariable String username, @RequestParam Integer age, @ApiParam("性别")@RequestParam("gender") String gender) {
        System.out.println("我在执行方法鸟~" + username + age);
        return ResultVO.success();
    }
}
