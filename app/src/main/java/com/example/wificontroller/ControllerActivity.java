package com.example.wificontroller;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ToggleButton;
import androidx.appcompat.app.AppCompatActivity;

import java.text.DecimalFormat;
import java.util.Locale;
import java.util.Objects;

import static android.text.TextUtils.split;
import static com.example.wificontroller.JoystickView.getsVMax;
import static java.lang.Thread.sleep;

public class ControllerActivity extends AppCompatActivity {

    private final Runnable directionToShoot = (new Runnable() {
        public boolean isRunning = true;
        public void run() {
            while (isRunning) {
                GameMessageManager.sendMessage("CBOT");
                String message = GameMessageManager.getNextMessage();
                assert message != null;
                if (!message.equals("EMPTY")) {
                    GameMessageManager.sendMessage("GunTrav=" + message.split("/")[0]);
                }
                try {
                    sleep(1);
                } catch (InterruptedException e) {
                    throw new RuntimeException(e);
                }
            }
        }
    });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);

        new Thread(directionToShoot).start();

        new Thread(() -> {
            Log.i("kikoo", getIntent().getStringExtra(MainActivity.NAME));
            if (!getIntent().getStringExtra(MainActivity.NAME).equals("")) {
                GameMessageManager.sendMessage("NAME=" + getIntent().getStringExtra(MainActivity.NAME) + "#COL=" + getIntent().getStringExtra(MainActivity.COLOR) + "#MSG=Salut#CBOT");
            } else {
                GameMessageManager.sendMessage("COL=" + getIntent().getStringExtra(MainActivity.COLOR) + "#MSG=Salut");
            }
            Log.i("cbot", GameMessageManager.getNextMessage());
        }).start();
        toggleAutoFire();
        actionFire();
        createJoystick();
        setUpEmotes();
        toggleHide();
        toggleTrav();
    }

    private void toggleTrav() {
        ToggleButton toggleButton = (ToggleButton) findViewById(R.id.guntrav);

        toggleButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                new Thread(new Runnable() {
                    public void run() {
                        boolean checked = ((ToggleButton) v).isChecked();
                        if (checked){
                            GameMessageManager.sendMessage("CBOT");
                            GameMessageManager.sendMessage("GunTrav="+ Objects.requireNonNull(GameMessageManager.getNextMessage()).split("/")[0]);
                        }
                        else{
                            GameMessageManager.sendMessage("GunTrav=1");
                        }
                    }
                }).start();

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
                    GameMessageManager.sendMessage("MotL=" + String.format(Locale.ENGLISH, "%.4f", Vg1)+"#MotR=" + String.format(Locale.ENGLISH, "%.4f", Vd1));
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
    public void onBackPressed() {
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        super.onBackPressed();
    }
}