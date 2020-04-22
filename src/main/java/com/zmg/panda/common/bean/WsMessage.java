package com.zmg.panda.common.bean;


import lombok.Data;

/**
 * @author Andy
 */
@Data
public class WsMessage {
    private String toUser;
    private String messageText;
}
