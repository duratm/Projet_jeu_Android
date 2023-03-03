package com.example.wificontroller;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class Setting extends AppCompatActivity {
    public final static String COLOR = "com.example.wificontroller.COLOR";
    public static int color = 4651321;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        initializeColorPicker();
    }

    private void initializeColorPicker() {
        final Button button = (Button) findViewById(R.id.colorPicker);
        final ColorPicker cp = new ColorPicker(Setting.this, 255, 0, 0, 0);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        cp.show();
                        cp.enableAutoClose();
                        cp.setCallback(new ColorPickerCallback() {
                            @Override
                            public void onColorChosen(int colour) {

                                Log.i("color", String.valueOf(cp.getColor()));
                                color = cp.getColor();
                            }
                        });
                    }
                });
    }


}
