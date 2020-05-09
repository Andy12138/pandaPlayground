package com.zmg.panda.manage.schedule;

import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.service.IRedisPublishService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * @author Andy
 */
@Component
public class RedisSchedule {

    public static final SimpleDateFormat SDF = new SimpleDateFormat("HH:mm:ss");

    @Autowired
    private IRedisPublishService iRedisPublishService;
//
//    @Scheduled(fixedRate = 2000)
//    public void publishUser() {
//        iRedisPublishService.sendMessage(RedisPojo.TOPIC_TITLE, "熊" + SDF.format(new Date()));
//    }
//
//    @Scheduled(fixedRate = 3000)
//    public void publishGroup() {
//        iRedisPublishService.sendMessage(RedisPojo.TOPIC_TITLE2, "猫" + SDF.format(new Date()));
//    }
}
