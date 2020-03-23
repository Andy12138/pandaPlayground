package com.zmg20200111.panda.utils.activeMq;

import javax.jms.JMSException;
import javax.jms.MessageProducer;
import javax.jms.Queue;
import javax.jms.TextMessage;

public class Producter extends BaseActiveMq {

    ThreadLocal<MessageProducer> threadLocal = new ThreadLocal<>();

    public void sendMessage(String disName) {
        try {
            // 创建一个消息队列
            Queue queue = session.createQueue(disName);
            // 消息生产者
            MessageProducer messageProducer = null;
            if (threadLocal.get() != null) {
                messageProducer = threadLocal.get();
            } else {
                messageProducer = session.createProducer(queue);
                threadLocal.set(messageProducer);
            }
            while (true) {
                Thread.sleep(5000);
                int num = count.getAndIncrement();
                // 创建一条消息
                TextMessage textMessage = session.createTextMessage(Thread.currentThread().getName() + "producter: 生产者正在生产产品！count = " + num);
                System.out.println(textMessage);
                // 发送消息
                messageProducer.send(textMessage);
                // 提交事务
//                session.commit();
            }
        } catch (JMSException | InterruptedException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Producter producter = new Producter();
        producter.init();
        producter.sendMessage("zmg_mq");
    }
}
