package com.zmg20200111.panda.utils.activeMq;

import javax.jms.*;

public class Consumer extends BaseActiveMq {

    ThreadLocal<MessageConsumer> threadLocal = new ThreadLocal<>();


    public void getMessage(String disName) {
        try {
            Queue queue = session.createQueue(disName);
            MessageConsumer messageConsumer = null;
//            if (threadLocal != null) {
//                messageConsumer = threadLocal.get();
//            } else {
                messageConsumer = session.createConsumer(queue);
//                threadLocal.set(messageConsumer);
//            }
            messageConsumer.setMessageListener(message -> {
                TextMessage textMessage = (TextMessage) message;
                try {
                    System.out.println(Thread.currentThread().getName() + "consumer: 消费者正在消费产品：" + textMessage.getText() + "--->" + count.getAndIncrement());
                } catch (JMSException e) {
                    e.printStackTrace();
                }
            });
//            while (true) {
//                Thread.sleep(1000);
//                TextMessage message = (TextMessage) messageConsumer.receive();
//                if (message != null) {
//                    message.acknowledge();
//                    System.out.println(Thread.currentThread().getName() + "consumer: 消费者正在消费产品：" + message.getText() + "--->" + count.getAndIncrement());
//                } else {
//                    break;
//                }
//            }
        } catch (JMSException  e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        Consumer consumer = new Consumer();
        consumer.init();
        consumer.getMessage("zmg_mq");
    }

}
