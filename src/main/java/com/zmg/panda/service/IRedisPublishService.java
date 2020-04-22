package com.zmg.panda.service;

/**
 * @author Andy
 */
public interface IRedisPublishService {

    <T> void sendMessage(String channel, T message);
}
