package com.zmg.panda.utils.activeMq;

import org.junit.jupiter.api.Test;

class BaseActiveMqTest {

    public static void main(String[] args) {
        Producter producter = new Producter();
        producter.init();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        for (int i = 0; i < 5; i++) {
            new Thread(()->{
                while (true) {
                    producter.sendMessage("名桂-MQ");
                    try {
                        Thread.sleep(10000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();
        }
    }

    @Test
    public void producterTest() {

    }

}