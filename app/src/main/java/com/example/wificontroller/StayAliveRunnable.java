package com.example.wificontroller;

import static java.lang.Thread.sleep;

public class StayAliveRunnable implements Runnable{

    private boolean isRunning = true;
    @Override
    public void run() {
        while (isRunning) {
                GameMessageManager.sendMessage("LIVE");
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
        }
    }

    public void stop(){
        try{
            isRunning = false;
            super.finalize();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

}
