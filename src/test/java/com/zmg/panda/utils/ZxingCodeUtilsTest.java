package com.zmg.panda.utils;

import org.junit.jupiter.api.Test;
import org.springframework.util.Base64Utils;

import static org.junit.jupiter.api.Assertions.*;

class ZxingCodeUtilsTest {

    private ZxingCodeUtils zxingCodeUtils = new ZxingCodeUtils();
    private String content = new String("{\"time\":\"2020-06-01-2020-07-01\",\"caseTotal\":4000,\"money\":1000,\"name\":\"宫本武藏\",\"id\":009}");
    private String content1 = new String("[\"2020-06-01-2020-07-01\",40000000,100000000,\"Andy\",009HDHD]");

    @Test
    public void test1() {
        String imgPath = "D:\\tmp\\条形码测试.png";
        System.out.println(content.length());
        String encode = Base64Utils.encodeToString(content.getBytes());
        System.out.println(Base64Utils.encodeToString("2020-08-09_ots".getBytes()));
        System.out.println(encode.length());
        System.out.println(encode);
        zxingCodeUtils.encode(content, 400, 100, imgPath);
        System.out.println("开始解析");
        String decode = zxingCodeUtils.decode(imgPath);
        System.out.println(decode);
    }

}