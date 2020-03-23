package com.zmg20200111.panda;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class PandaApplication {

    public static void main(String[] args) {
        System.out.println("我是最最棒！");
        SpringApplication.run(PandaApplication.class, args);
    }

}
