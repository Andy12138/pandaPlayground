package com.zmg.panda.conf;

import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.service.IRedisReceiverService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

import java.util.Arrays;

/**
 * @author Andy
 */
@Configuration
public class RedisSubscriberConf {

    /**
     * 绑定消息监听者和接受监听的方法
     * @param receiver
     * @return
     */
    @Bean
    public MessageListenerAdapter listenerAdapter(IRedisReceiverService receiver) {
        // 自定义消息监听的处理方式
        return new MessageListenerAdapter(receiver, "receiveMessage");
    }

    /**
     * 创建消息监听容器
     */
    @Bean
    public RedisMessageListenerContainer getRedisMessageListenerContainer(RedisConnectionFactory redisConnectionFactory, MessageListenerAdapter listenerAdapter) {
        RedisMessageListenerContainer container = new RedisMessageListenerContainer();
        container.setConnectionFactory(redisConnectionFactory);
        container.addMessageListener(listenerAdapter, Arrays.asList(new PatternTopic(RedisPojo.TOPIC_TITLE),
                new PatternTopic(RedisPojo.TOPIC_TITLE2)));
        return container;
    }
}
