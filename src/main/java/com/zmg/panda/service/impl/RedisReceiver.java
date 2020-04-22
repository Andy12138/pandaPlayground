package com.zmg.panda.service.impl;

import org.springframework.stereotype.Component;

/**
 * @author Andy
 */
@Component
public class RedisReceiver {

    public <T> void receiveMessage(T message) {
        System.out.println(message);
    }
}
