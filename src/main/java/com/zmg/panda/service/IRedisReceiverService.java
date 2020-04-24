package com.zmg.panda.service;

import javax.servlet.http.HttpServletRequest;

/**
 * @author Andy
 */
public interface IRedisReceiverService {

    /**
     * 接收消息
     * @param message
     * @param <T>
     */
    <T> void receiveMessage(String message);
}
