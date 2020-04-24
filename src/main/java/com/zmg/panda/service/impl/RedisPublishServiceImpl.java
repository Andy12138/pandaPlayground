package com.zmg.panda.service.impl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zmg.panda.service.IRedisPublishService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

/**
 * @author Andy
 */
@Slf4j
@Service
public class RedisPublishServiceImpl implements IRedisPublishService {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public <T> void sendMessage(String channel, T param) {
        String message = JSONObject.toJSONString(param);
        redisTemplate.convertAndSend(channel, message);
    }
}
