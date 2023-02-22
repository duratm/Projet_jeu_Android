package com.example.wificontroller;

import static java.lang.Float.parseFloat;
import static java.lang.Integer.parseInt;
import static java.lang.Integer.valueOf;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

import java.text.Normalizer;
import java.util.Objects;

public class FirstController extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_controller);

        Button avancer = findViewById(R.id.avancer);
        Button droite = findViewById(R.id.droite);
        Button gauche = findViewById(R.id.gauche);
        Button reculer = findViewById(R.id.reculer);
        GameMessageManager.connect();

        Button tir = findViewById(R.id.tir);
        Log.i("passage thread", "oui");

        while (!GameMessageManager.isConnected()){
            try {
                Thread.sleep(10);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
        new Thread(new ProducteurPosition()).start();
        tir.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View view, MotionEvent motionEvent) {
                if (motionEvent.getAction() == MotionEvent.ACTION_DOWN){
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("GunTrig=1");
                        }
                    }).start();
                }
                else if (motionEvent.getAction() == MotionEvent.ACTION_UP){
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("GunTrig=0");
                        }
                    }).start();
                }
                return true;
            }
        });
        avancer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        @SuppressLint("ResourceType")
                        public void run() {
                            Positions positions = new Positions();
                            Float orient;
                            try {
                                orient = positions.recupere();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            int dir = changeDirection(orient);

//                            Log.i("direction", String.valueOf(dir));
                            if (dir == 0){
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.75");
                            }
                            if (dir == 1){
                                GameMessageManager.sendMessage("MotL=0.75#MotR=0.25");
                            }
                            if (dir==2){
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.25");
                            }
                            if (dir==3){
                                GameMessageManager.sendMessage("MotR=0.25#MotL=0.25");
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5");
                        }
                    }).start();
                }
                return true;
            }
        });


        droite.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        public void run() {
                            Positions positions = new Positions();
                            Float orient;
                            try {
                                orient = positions.recupere();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            int dir = changeDirection(orient);
                            try {
                                Thread.sleep(1000);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
//                            Log.i("direction", String.valueOf(dir));
                            if (dir == 0){
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.75");
                            }
                            if (dir == 1){
                                GameMessageManager.sendMessage("MotL=0.75#MotR=0.25");
                            }
                            if (dir==2){
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.25");
                            }
                            if (dir==3){
                                GameMessageManager.sendMessage("MotR=0.25#MotL=0.25");
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5");
                        }
                    }).start();
                }
                return true;
            }
        });

        gauche.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        public void run() {
                            Positions positions = new Positions();
                            Float orient;
                            try {
                                orient = positions.recupere();
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }

                            int dir = changeDirection(orient);
//                            Log.i("direction", String.valueOf(dir));
                            if (dir == 0) {
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.75");
                            }
                            if (dir == 1) {
                                GameMessageManager.sendMessage("MotL=0.75#MotR=0.25");
                            }
                            if (dir == 2) {
                                GameMessageManager.sendMessage("MotR=0.75#MotL=0.25");
                            }
                            if (dir == 3) {
                                GameMessageManager.sendMessage("MotR=0.25#MotL=0.25");
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5");
                        }
                    }).start();
                }
                return true;
            }
        });

    }


    @SuppressLint("CutPasteId")
    private int changeDirection(Float nextMessage) {
//        Log.i("nextMessage", String.valueOf(nextMessage));
        if (nextMessage != null){
            float nextMessageInt = nextMessage;

            if (nextMessageInt > 0.125 && nextMessageInt < 0.375){
                return 0;
            }
            if (nextMessageInt > 0.375 && nextMessageInt < 0.875){
                return 1;
            }
            if (nextMessageInt > 0.125 && nextMessageInt < 0.625){
                return 2;
            }
            if (nextMessageInt > 0.625 && nextMessageInt < 0.875) {
                return 3;
            }
        }
        return -1;
    }
}
