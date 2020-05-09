package com.zmg.panda.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.zmg.panda.common.bean.OnlineUser;
import com.zmg.panda.common.bean.WsMessage;
import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.conf.EnvConf;
import com.zmg.panda.service.IRedisReceiverService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * @author Andy
 */
@Slf4j
@Service
public class RedisReceiverServiceImpl implements IRedisReceiverService {

    @Autowired
    private StringRedisTemplate redisTemplate;
    @Autowired
    private SimpMessagingTemplate messagingTemplate;
    @Autowired
    private EnvConf envConf;

    @Override
    public <T> void receiveMessage(String message, String channel) {
        log.info("当前频道：{}, 接收到消息：{}", channel, message);
        WsMessage wsMessage = JSONObject.parseObject(message, WsMessage.class);
        String toUser = wsMessage.getToUser();
        Long onlineNum = redisTemplate.opsForList().size(RedisPojo.ONLINE_USERS);
        if (null != onlineNum && onlineNum > 0) {
            List<String> usersJson = redisTemplate.opsForList().range(RedisPojo.ONLINE_USERS, 0, onlineNum - 1);
            usersJson.forEach(e ->{
                OnlineUser onlineUser = JSONObject.parseObject(e, OnlineUser.class);
                // 是本机登陆用户，则发送消息
                if (onlineUser.getUsername().equals(toUser) && onlineUser.getHostPort().equals(envConf.getHostPort())) {
                    messagingTemplate.convertAndSendToUser(wsMessage.getToUser(), "/userTest/callBack", wsMessage.getMessageText());
                }
            });
        }
    }
}
