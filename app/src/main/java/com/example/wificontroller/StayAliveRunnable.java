package com.example.wificontroller;

import static java.lang.Thread.sleep;

public class StayAliveRunnable implements Runnable{

    @Override
    public void run() {
        while (true) {
                GameMessageManager.sendMessage("LIVE");
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
    }

}
