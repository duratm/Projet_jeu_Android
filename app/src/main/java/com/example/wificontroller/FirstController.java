package com.example.wificontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;

public class FirstController extends AppCompatActivity {

    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_controller);

        Button avancer = findViewById(R.id.avancer);
        Button droite = findViewById(R.id.droite);
        Button gauche = findViewById(R.id.gauche);
        GameMessageManager.connect();

        Button tir = findViewById(R.id.tir);

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
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.75#MotL=0.75#ORIENT");
                            Log.i("oriant", "" + GameMessageManager.getNextMessage());
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
                            GameMessageManager.sendMessage("MotR=0.75#MotL=0.25");
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
                            GameMessageManager.sendMessage("MotL=0.75#MotR=0.25");
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


}
