package com.zmg20200111.panda.manage.bean;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;

@Data
@Configuration
@ConfigurationProperties(prefix = "app.auth")
public class AuthParametersConf {
    private String jwtTokenSecret;
    private Long tokenExpiredMs;
}
