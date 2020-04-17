package com.zmg.panda.controller;

import com.zmg.panda.bean.ResultVO;
import com.zmg.panda.utils.file.FileUtils;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.Map;

/**
 * @author Andy
 */
@RestController
@RequestMapping("/api/zmg")
public class ZmgController {

    @GetMapping("/hello")
    public String hello() {
        return "hello zmg";
    }

    @RequestMapping("/preview1")
    public void er(HttpServletResponse response){
        String filePath = "E:\\视频教学\\springboot01\\源码、资料、课件\\SpringBoot课件.pdf";
        FileUtils fileUtils = new FileUtils();
        fileUtils.previewPdf(filePath, response);
    }


    @RequestMapping("/helloMe")
    public ResultVO helloMe() {
        Map<String, String> map = new HashMap<>();
        map.put("name", "钟名桂");
        map.put("gender", "男");
        map.put("birthday", "2020-02-14");
        ResultVO resultVO = new ResultVO();
        resultVO.setData(map);
        resultVO.setCode(1);
        resultVO.setMessage("你成功了！");
        return resultVO;
    }

    @RequestMapping("/myHost")
    public void getHost(HttpServletRequest request) {
        System.out.println("进来了");
        System.out.println("本机ip" + request.getLocalAddr());
        System.out.println("本机port" + request.getLocalPort());
        System.out.println("目标机ip" + request.getRemoteAddr());
        System.out.println("目标机ip" + request.getRemoteHost());
        System.out.println("目标机port" + request.getRemotePort());
    }


    @PreAuthorize("hasAuthority('admin')")
    @GetMapping("/testRole")
    public ResultVO testRole() {
        return ResultVO.success("你有资格调用这个接口！");
    }
}
