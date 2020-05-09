package com.zmg.panda.service;

/**
 * @author Andy
 */
public interface IRedisReceiverService {

    /**
     * 接收消息
     * @param message
     * @param channel
     * @param <T>
     */
    <T> void receiveMessage(String message, String channel);
}
