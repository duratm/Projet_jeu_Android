package com.example.wificontroller;

import android.os.Bundle;
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
        setContentView(R.layout.activity_settings);
        initializeColorPicker();
    }

    private void initializeColorPicker() {
        Button button = (Button) findViewById(R.id.button2);
        final ColorPicker cp = new ColorPicker(Setting.this, 255, 0, 0, 0);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        cp.show();
                        cp.enableAutoClose();
                        cp.setCallback(new ColorPickerCallback() {
                            @Override
                            public void onColorChosen(int colour) {

                                color = cp.getColor();
                            }
                        });
                    }
                });
    }


}
