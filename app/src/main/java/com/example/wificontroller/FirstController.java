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
        avancer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.75#MotL=0.75");
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
                    Log.i("appui", "il vient  d'appuyer a droite");
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5");
                        }
                    }).start();
                    Log.i("relache", "il vient  de relacher a droite");
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
