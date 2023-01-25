package com.example.wificontroller;

import android.util.Log;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import static com.example.wificontroller.JoystickView.getsVMax;

public class ControllerActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_controller);
        JoystickView.ValueChangedHandler value = (Vg, Vd) -> {
            Log.i("kikoo", Vg+" "+Vd);
        };
        FrameLayout frame = findViewById(R.id.frame);
        frame.addView(new JoystickView(this,value));
    }
}