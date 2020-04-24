package com.zmg.panda.manage.websocket;

import com.alibaba.fastjson.JSONObject;
import com.zmg.panda.common.bean.OnlineUser;
import com.zmg.panda.common.constants.RedisPojo;
import com.zmg.panda.conf.EnvConf;
import com.zmg.panda.manage.auth.JwtTokenProvider;
import com.zmg.panda.manage.bean.WebsocketUserAuthentication;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.messaging.Message;
import org.springframework.messaging.MessageChannel;
import org.springframework.messaging.simp.stomp.StompCommand;
import org.springframework.messaging.simp.stomp.StompHeaderAccessor;
import org.springframework.messaging.support.ChannelInterceptor;
import org.springframework.messaging.support.MessageHeaderAccessor;

import java.util.Date;
import java.util.List;

import static com.zmg.panda.manage.auth.JwtTokenProvider.TOKEN_PREFIX;


/**
 * @author Andy
 */
@Slf4j
public class WebsocketChannelInterceptor implements ChannelInterceptor {

    @Autowired
    private JwtTokenProvider jwtTokenProvider;
    @Autowired
    private EnvConf envConf;
    @Autowired
    private StringRedisTemplate redisTemplate;

    /**
     * 消息发送之前调用
      */
    @Override
    public Message<?> preSend(Message<?> message, MessageChannel channel) {
        StompHeaderAccessor accessor = MessageHeaderAccessor.getAccessor(message, StompHeaderAccessor.class);
        if (StompCommand.CONNECT.equals(accessor.getCommand())) {
            log.info("开始连接");
            /*
             * 1. 这里获取就是JS stompClient.connect(headers, function (frame){.......}) 中header的信息
             * 2. JS中header可以封装多个参数，格式是{key1:value1,key2:value2}
             * 3. header参数的key可以一样，取出来就是list
             * 4. 样例代码header中只有一个token，所以直接取0位
             */
            String token = accessor.getFirstNativeHeader("auth-token");
            if (StringUtils.isNotBlank(token) && jwtTokenProvider.validateToken(token.replace(TOKEN_PREFIX, ""))) {
                WebsocketUserAuthentication user = (WebsocketUserAuthentication)accessor.getUser();
                validateEnableUser(user);
                // 将登陆用户放入redis在线用户list
                OnlineUser onlineUser = new OnlineUser();
                onlineUser.setUsername(user.getName());
                onlineUser.setHostPort(envConf.getHostPort());
                onlineUser.setLoginTime(new Date());
                redisTemplate.opsForList().rightPush(RedisPojo.ONLINE_USERS, JSONObject.toJSONString(onlineUser));
                log.info("认证用户:{}", user);
                log.info("页面传递令牌:{}", token);
            } else {
                log.error("websocket token 认证失败！");
                throw new RuntimeException("websocket token 认证失败！");
            }
        }
        if (StompCommand.SEND.equals(accessor.getCommand())) {
            log.info(accessor.getUser().getName() + "发送消息命令");
        }
        if (StompCommand.DISCONNECT.equals(accessor.getCommand())) {
            log.info(accessor.getUser().getName() + "断开连接");
            Long onlineNum = redisTemplate.opsForList().size(RedisPojo.ONLINE_USERS);
            List<String> onlineUsersStr = redisTemplate.opsForList().range(RedisPojo.ONLINE_USERS, 0, onlineNum - 1);
            if (onlineUsersStr != null) {
                onlineUsersStr.forEach(e -> {
                    OnlineUser onlineUser = JSONObject.parseObject(e, OnlineUser.class);
                    if (onlineUser.getUsername().equals(accessor.getUser().getName())) {
                        // 删除，表示下线
                        redisTemplate.opsForList().remove(RedisPojo.ONLINE_USERS, 0, e);
                    }
                });
            }
        }
        return message;
    }

    private void validateEnableUser(WebsocketUserAuthentication user) {
        if (user == null) {
            throw new RuntimeException("您还未登录即时通讯！");
        }
    }

    /**
     * 1. 在消息发送完成后调用，而不管消息发送是否产生异常，在次方法中，我们可以做一些资源释放清理的工作
     * 2. 此方法的触发必须是preSend方法执行成功，且返回值不为null,发生了实际的消息推送，才会触发
     */
    @Override
    public void afterSendCompletion(Message<?> message, MessageChannel messageChannel, boolean b, Exception e) {

    }

    /**
     *  1. 在消息被实际检索之前调用，如果返回false,则不会对检索任何消息，只适用于(PollableChannels)，
     * 2. 在websocket的场景中用不到
     */
    @Override
    public boolean preReceive(MessageChannel messageChannel) {
        return true;
    }

    /**
     * 1. 在检索到消息之后，返回调用方之前调用，可以进行信息修改，如果返回null,就不会进行下一步操作
     * 2. 适用于PollableChannels，轮询场景
     */
    @Override
    public Message<?> postReceive(Message<?> message, MessageChannel messageChannel) {
        return message;
    }

    /**
     * 1. 在消息接收完成之后调用，不管发生什么异常，可以用于消息发送后的资源清理
     * 2. 只有当preReceive 执行成功，并返回true才会调用此方法
     * 2. 适用于PollableChannels，轮询场景
     */
    @Override
    public void afterReceiveCompletion(Message<?> message, MessageChannel messageChannel, Exception e) {

    }
}
