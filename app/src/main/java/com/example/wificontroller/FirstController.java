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
                            GameMessageManager.sendMessage("MotR=0.75#MotL=0.75#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            Log.i("reviens", ""+buttons[0].getId());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
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
                            GameMessageManager.sendMessage("MotR=0.75#MotL=0.25#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
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
                            GameMessageManager.sendMessage("MotL=0.75#MotR=0.25#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
                        }
                    }).start();
                } else if (event.getAction() == MotionEvent.ACTION_UP) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0.5#MotL=0.5#ORIENT");
                            Button[] buttons = changeDirection(GameMessageManager.getNextMessage());
                            if (buttons[0] != null || buttons[1] != null || buttons[2] != null || buttons[3] != null) {
                                assert buttons[0] != null;
                                avancer.setId(buttons[0].getId());
                                gauche.setId(buttons[1].getId());
                                droite.setId(buttons[2].getId());
                                reculer.setId(buttons[3].getId());
                            }
                        }
                    }).start();
                }
                return true;
            }
        });
    }


    @SuppressLint("CutPasteId")
    private Button[] changeDirection(String nextMessage) {
        Button[] buttons = new Button[4];
        Button avancer = null;
        Button gauche = null;
        Button droite = null;
        Button reculer = null;
        if (!nextMessage.isEmpty()){
            Log.i("passage", "passage dans changeDirection");
            float nextMessageInt = parseFloat(nextMessage);

            if (nextMessageInt > 0.125 && nextMessageInt < 0.375){
                avancer = findViewById(R.id.avancer);
                gauche = findViewById(R.id.gauche);
                droite = findViewById(R.id.droite);
                reculer = findViewById(R.id.reculer);
            }
            if (nextMessageInt > 0.375 && nextMessageInt < 0.875){
                avancer = findViewById(R.id.gauche);
                gauche = findViewById(R.id.reculer);
                droite = findViewById(R.id.avancer);
                reculer = findViewById(R.id.droite);
            }
            if (nextMessageInt > 0.125 && nextMessageInt < 0.625){
                avancer = findViewById(R.id.droite);
                gauche = findViewById(R.id.avancer);
                droite = findViewById(R.id.reculer);
                reculer = findViewById(R.id.gauche);
            }
            if (nextMessageInt > 0.625 && nextMessageInt < 0.875) {
                avancer = findViewById(R.id.reculer);
                gauche = findViewById(R.id.gauche);
                droite = findViewById(R.id.droite);
                reculer = findViewById(R.id.avancer);
            }
            buttons[0] = avancer;
            buttons[1] = gauche;
            buttons[2] = droite;
            buttons[3] = reculer;
        }
        return buttons;
    }
}
