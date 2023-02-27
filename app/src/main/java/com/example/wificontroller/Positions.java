package com.example.wificontroller;

import android.util.Log;

import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.TimeUnit;

public class Positions {

    private BlockingQueue<Float> queue =  new ArrayBlockingQueue<Float>(4) ;

    public synchronized void depose(Float position) throws InterruptedException {
        Log.i("depose", String.valueOf(position));
        //queue.offer(position,  200, TimeUnit.MILLISECONDS);
        queue.put(position);
    }

    public synchronized Float recupere() throws InterruptedException {
        //String pos = String.valueOf(this.queue.poll(1000, TimeUnit.MILLISECONDS));
        String pos = String.valueOf(this.queue.take());
        //Log.i("der pos", pos);
        if (pos != null) {
            return Float.parseFloat(pos);
        }
        return 0.5f;
    }
}
