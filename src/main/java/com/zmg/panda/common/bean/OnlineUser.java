package com.zmg.panda.common.bean;

import lombok.Data;

import java.util.Date;

/**
 * @author Andy
 */
@Data
public class OnlineUser {
    private String username;
    private String hostPort;
    private Date loginTime;
}
