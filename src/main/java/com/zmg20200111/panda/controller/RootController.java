package com.zmg20200111.panda.controller;

import com.zmg20200111.panda.bean.ResultVO;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author Andy
 */
@RestController
@RequestMapping("/api/root")
public class RootController {

    @GetMapping("/rootShow")
    public ResultVO rootShow() {
        return ResultVO.success("欢迎超级ROOT！");
    }
}
