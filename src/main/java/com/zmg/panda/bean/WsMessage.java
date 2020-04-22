package com.zmg.panda.bean;


import lombok.Data;

/**
 * @author Andy
 */
@Data
public class WsMessage {
    private String toUser;
    private String messageText;
}
