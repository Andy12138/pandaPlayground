package com.zmg.panda.conf;

import com.zmg.panda.common.constants.TopicName;
import com.zmg.panda.service.impl.RedisReceiver;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.data.redis.listener.PatternTopic;
import org.springframework.data.redis.listener.RedisMessageListenerContainer;
import org.springframework.data.redis.listener.adapter.MessageListenerAdapter;

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
    public MessageListenerAdapter listenerAdapter(RedisReceiver receiver) {
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
        container.addMessageListener(listenerAdapter, new PatternTopic(TopicName.TOPIC_TITLE));
        return container;
    }
}
