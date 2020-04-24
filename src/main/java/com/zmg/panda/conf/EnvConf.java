package com.zmg.panda.conf;

import lombok.Data;
import lombok.ToString;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.net.InetAddress;
import java.net.UnknownHostException;

/**
 * @author Andy
 */
@Slf4j
@Component
@ToString
public class EnvConf {
    private String host;
    private String port;
    private String hostPort;

    @Autowired
    private Environment environment;

    @PostConstruct
    public void init() {
        log.info("开始初始化项目的ip");
        try {
            this.host = InetAddress.getLocalHost().getHostAddress();
        } catch (UnknownHostException e) {
            throw new RuntimeException("无法获取本项目IP");
        }
    }

    public String getHost() {
        return this.host;
    }

    public String getPort() {
        return environment.getProperty("local.server.port");
    }

    public String getHostPort() {
        return this.getHost() + ":" + this.getPort();
    }
}
