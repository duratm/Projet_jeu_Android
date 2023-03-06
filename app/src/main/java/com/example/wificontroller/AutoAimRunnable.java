package com.example.wificontroller;

import static java.lang.Thread.sleep;

import android.util.Log;

import java.util.Locale;

public class AutoAimRunnable implements Runnable{

    public boolean isRunning;

    private boolean stop = false;

    public AutoAimRunnable (){
        isRunning = true;
    }

    @Override
    public void run() {
        while (! stop) {
            if(isRunning) {
                GameMessageManager.sendMessage("CBOT");
                try {
                    sleep(5);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                String message = GameMessageManager.getNextMessage();
                assert message != null;
                if (!message.contains("EMPTY")) {
                    Log.i("oeeee", String.format(Locale.ENGLISH, "%.4f", Double.parseDouble(message.split("/")[0])));
                    //                   GameMessageManager.sendMessage("GunTrav=" + String.format(Locale.ENGLISH, "%.4f", Double.parseDouble(message.split("/")[0])));
                    GameMessageManager.sendMessage("GunTrav=" + message.split("/")[0]);
                }
            }
        }
    }

    public void stop(){
        try{
            setRunning(false);
            stop = true;
            sleep(5);
            super.finalize();
        }
        catch (Throwable e){
            e.printStackTrace();
        }
    }

    public void setRunning(boolean running) {
        isRunning = running;
    }
}
