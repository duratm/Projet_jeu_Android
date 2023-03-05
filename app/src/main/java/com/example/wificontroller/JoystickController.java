package com.example.wificontroller;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ToggleButton;

import static com.example.wificontroller.JoystickView.getsVMax;
import static java.lang.Thread.sleep;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;

import android.app.Activity;

public class JoystickController extends FragmentActivity {

    private final StayAliveRunnable stayAlive = new StayAliveRunnable();
    private final AutoAimRunnable directionToShoot = new AutoAimRunnable();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        new Thread(stayAlive).start();
        new Thread(directionToShoot).start();

        toggleAutoFire();
        actionFire();
        setUpEmotes();
        toggleHide();
        toggleTrav();
        createJoystick();

    }

    private void toggleTrav() {
        ToggleButton toggleButton = findViewById(R.id.guntrav);
        toggleButton.setChecked(true);
        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                boolean checked = ((ToggleButton) v).isChecked();
                if (checked){
                    directionToShoot.setRunning(true);
                }
                else{
                    directionToShoot.setRunning(false);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            try {
                                sleep(50);
                            } catch (InterruptedException e) {
                                throw new RuntimeException(e);
                            }
                            GameMessageManager.sendMessage("GunTrav=0.5");
                        }
                    }).start();
                }
            }
        });
    }


    private void toggleHide() {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.Hide);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        boolean checked = ((ToggleButton) v).isChecked();
                        if (checked){
                            GameMessageManager.sendMessage("COL=-16777216");
                        }
                        else{
                            GameMessageManager.sendMessage("COL=" + getIntent().getStringExtra(MainActivity.COLOR));
                        }
                    }
                }).start();

            }
        });
    }

    private void setUpEmotes() {
        Button button = findViewById(R.id.bottomEmoteButton);
        Button finalButton = button;
        button.setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("MSG="+finalButton.getText().toString());
                }
            }).start();
        });
        button = findViewById(R.id.topEmoteButton);
        Button finalButton1 = button;
        button.setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("MSG="+ finalButton1.getText().toString());
                }
            }).start();
        });
        button = findViewById(R.id.rightEmoteButton);
        Button finalButton2 = button;
        button.setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("MSG="+ finalButton2.getText().toString());
                }
            }).start();
        });
        button = findViewById(R.id.leftEmoteButton);
        Button finalButton3 = button;
        button.setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("MSG="+ finalButton3.getText().toString());
                }
            }).start();
        });
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
                            GameMessageManager.sendMessage("GunTrig=1");
                        }
                        else{
                            GameMessageManager.sendMessage("GunTrig=0");
                        }
                    }
                }).start();

            }
        });
    }

    private void createJoystick() {
        JoystickView.ValueChangedHandler value = (Vg, Vd) -> {
            double Vg1 = ((double) Vg/getsVMax())+0.5;
            double Vd1 = ((double) Vd/getsVMax())+0.5;
            new Thread(new Runnable() {
                public void run() {
                    Log.i("kikoo", Vg1+"#MotR=" + Vd1);
                    GameMessageManager.sendMessage("MotL=" + Vg1+"#MotR=" + Vd1);
                }
            }).start();
        };
        Log.i("kikoo", String.valueOf(GameMessageManager.isConnected()));
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
                    GameMessageManager.sendMessage("GunTrig=1");
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    GameMessageManager.sendMessage("GunTrig=0#Guntrav=0.5");
                    Log.i("kikoo", String.valueOf(GameMessageManager.getNextMessage()));
                }
            }).start();
        });
    }

    @Override
    protected void onDestroy() {
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        super.onDestroy();
        finish();
    }


    @Override
    public void onBackPressed() {
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        super.onBackPressed();
        finish();
    }
}