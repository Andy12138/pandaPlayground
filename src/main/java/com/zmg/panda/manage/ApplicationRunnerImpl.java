package com.zmg.panda.manage;

import com.zmg.panda.common.constants.RedisPojo;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

/**
 * @author Andy
 * 在项目启动后执行的类
 */
@Slf4j
@Component
public class ApplicationRunnerImpl implements ApplicationRunner {

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) throws Exception {
        // redis在线用户全部清空
        this.offlineUsers();
    }

    /**
     * redis在线用户全部清空
     */
    private void offlineUsers() {
        log.info("聊天用户全部下线------->");
        redisTemplate.opsForList().trim(RedisPojo.ONLINE_USERS, 0, 0);
        redisTemplate.opsForList().leftPop(RedisPojo.ONLINE_USERS);
    }
}
