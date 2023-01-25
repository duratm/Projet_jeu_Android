package com.example.wificontroller;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

    }

    public void control(View view){
        Intent intent = new Intent(this, ControllerActivity.class);
        startActivity(intent);
    }
}