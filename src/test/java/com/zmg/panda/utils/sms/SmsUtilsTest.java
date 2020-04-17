package com.zmg.panda.utils.sms;

import com.github.qcloudsms.SmsSingleSender;
import com.github.qcloudsms.SmsSingleSenderResult;
import com.github.qcloudsms.httpclient.HTTPException;
import org.junit.jupiter.api.Test;

import java.io.IOException;

import static org.junit.jupiter.api.Assertions.*;

class SmsUtilsTest {

    @Test
    public void sendMessageTest() throws HTTPException, IOException {
        SmsSingleSender sender = new SmsSingleSender(1400136050, "dd061aea6bc9e5519a45f14ef318b7a8");
        SmsSingleSenderResult send = sender.send(0, "86", "17620867642", "【易简网】您申请的验证码是：催办信息", "", "");
        System.out.println(send.toString());
    }

}