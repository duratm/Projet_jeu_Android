package com.example.wificontroller;

import static java.lang.Thread.sleep;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Button;
import android.widget.ToggleButton;

public class ButtonController extends AppCompatActivity {

    private final StayAliveRunnable stayAlive = new StayAliveRunnable();
    @SuppressLint("ClickableViewAccessibility")
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_first_controller);

        new Thread(stayAlive).start();
        actionFire();
        setUpEmotes();
        Button avancer = findViewById(R.id.avancer);
        Button droite = findViewById(R.id.droite);
        Button gauche = findViewById(R.id.gauche);
        Button reculer = findViewById(R.id.reculer);
        avancer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=1#MotL=1");
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

        reculer.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                if (event.getAction() == MotionEvent.ACTION_DOWN) {
                    new Thread(new Runnable() {
                        public void run() {
                            GameMessageManager.sendMessage("MotR=0#MotL=0");
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
    private void actionFire() {
        Button button = (Button) findViewById(R.id.fire);
        button.setOnClickListener(v -> {
            new Thread(new Runnable() {
                public void run() {
                    GameMessageManager.sendMessage("GunTrig=1");
                    try {
                        sleep(10);
                    } catch (InterruptedException e) {
                        throw new RuntimeException(e);
                    }
                    GameMessageManager.sendMessage("GunTrig=0");
                }
            }).start();
        });
    }

    @Override
    protected void onDestroy() {
        try {
            this.stayAlive.stop();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        super.onDestroy();
    }

    @Override
    public void onBackPressed() {
        try {
            this.stayAlive.stop();
        } catch (Throwable e) {
            throw new RuntimeException(e);
        }
        new Thread(new Runnable() {
            public void run() {
                GameMessageManager.sendMessage("EXIT");
            }
        }).start();
        //Intent intent = new Intent(this, MainActivity.class);
        //startActivity(intent);
        super.onBackPressed();
    }
}
