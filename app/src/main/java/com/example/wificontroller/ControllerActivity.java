package com.example.wificontroller;

import android.annotation.SuppressLint;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;

import static com.example.wificontroller.JoystickView.getsVMax;
import static java.lang.Thread.sleep;

public class ControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        new Thread(new Runnable() {
            public void run() {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
                GameMessageManager.sendMessage("NAME=Mathias#COL=30945#MSG=Salut#ORIENT#GunTrav=1");
                //Log.i("kikoo", String.valueOf(GameMessageManager.getNextMessage()));

            }
        }).start();
        toggleAutoFire();
        actionFire();
        createJoystick();

    }

    private void toggleAutoFire() {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.AutoFire);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        boolean checked = ((ToggleButton) v).isChecked();
                        if (checked){
                            GameMessageManager.sendMessage("GunTrav=1");
                        }
                        else{
                            GameMessageManager.sendMessage("GunTrav=0");
                        }
                    }
                }).start();

            }
        });
    }

    private void createJoystick() {
        JoystickView.ValueChangedHandler value = (Vg, Vd) -> {
            DecimalFormat f = new DecimalFormat();
            f.setMaximumFractionDigits(1);
            double Vg1 = (double) Vg/getsVMax()+0.5;
            double Vd1 = (double) Vd/getsVMax()+0.5;

            if (Vg1 < 0.5 && Vd1 < 0) {
                Vg1 = -Vg1;
                Vd1 = -Vd1;
            } else if (Vg1 < 0 || Vd1 < 0) {
                Vg1 = Vd1<0 ? -Vd1:Vd1;
                Vd1 = (double) Vg/getsVMax()+0.5<0 ? -(double) Vg/getsVMax()+0.5:(double) Vg/getsVMax()+0.5;
            }


            //Log.i("kikoo", "MotL=" + f.format(Vg)+"#MotR=" + f.format(Vd));
            //Log.i("kikoo",(double) Vg/getsVMax()+0.5+"-"+((double) Vd/getsVMax()+0.5));
            double finalVg = Vg1;
            double finalVd = Vd1;
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("MotL=" + f.format(finalVg)+"#MotR=" + f.format(finalVd));
                }
            }).start();
        };
        //Log.i("kikoo", String.valueOf(GameMessageManager.isConnected()));
        FrameLayout frame = findViewById(R.id.frame);
        JoystickView joystick =  new JoystickView(this,value);
        joystick.setJoystickRadius(300);
        frame.addView(joystick);
    }

    private void actionFire() {
        @SuppressLint({"MissingInflatedId", "LocalSuppress"}) Button button = findViewById(R.id.fire);
        button.setOnClickListener(v -> {
            ToggleButton toggleButton = (ToggleButton) findViewById(R.id.AutoFire);
            toggleButton.setChecked(false);
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("GunTrav=1");
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    GameMessageManager.sendMessage("GunTrav=0#ORIENT");
                    //Log.i("kikoo", String.valueOf(GameMessageManager.getNextMessage()));
                }
            }).start();
        });
    }

    @Override
    public void onBackPressed() {
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        super.onBackPressed();
    }
}