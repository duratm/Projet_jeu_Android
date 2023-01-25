package com.example.wificontroller;

import android.os.Bundle;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;


public class ControllActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        JoystickView.ValueChangedHandler value = new JoystickView.ValueChangedHandler() {
            @Override
            public void onValueChanged(int Vg, int Vd) {
                int md = Vd;
                int mg = Vg;
            }
        };
        new JoystickView(this, value);

    }


}
