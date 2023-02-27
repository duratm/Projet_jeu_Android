package com.example.wificontroller;

import android.util.Log;

public class ProducteurPosition implements Runnable {

    Positions positionsQueue = new Positions();

    public void run() {

        while (true) {
            Log.i("passage prod", "oui");

            GameMessageManager.sendMessage("ORIENT");

            Log.i("send message", "oui");
            try {
                Thread.sleep(1000);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
            //Log.i("der ori", "oui");
            // toutes les secondes un boulanger produit un pain
            if (GameMessageManager.isConnected()){
                Log.i("Connected", "oui");
            }
            else {
                Log.i("Connected", "oui");
            }
            String orient = GameMessageManager.getNextMessage();
            //Log.i("der ori 2", "oui");
            if (orient != null) {
                Log.i("orient != null", "oui");
                try {
                    positionsQueue.depose(Float.parseFloat(orient));
                } catch (InterruptedException e) {
                    Log.i("erreur", String.valueOf(e));
                    throw new RuntimeException(e);
                }
            }
        }
    }
}
