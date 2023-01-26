package com.example.wificontroller;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import android.content.Intent;
import android.view.View;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.TextView;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

public class MainActivity extends AppCompatActivity {

    public final static String COLOR = "com.example.wificontroller.COLOR";
    public static final String NAME = "com.example.wificontroller.NAME";
    int color = 4651321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.connect);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView textFromInput = findViewById(R.id.ipServer);
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        GameMessageManager.connect(adressServer);
                        control(v);
                        //GameMessageManager.sendMessage("MOTL=0.5");
                    }
                        /*else {
                            TextView errorText = findViewById(R.id.error);
                            errorText.setText("Une erreur est apparue lors de la connexion au serveur");
                        }*/
                });
        initializeColorPicker();
    }

    private void initializeColorPicker() {
        final Button button = findViewById(R.id.colorPicker);
        final ColorPicker cp = new ColorPicker(MainActivity.this, 255, 0, 0, 0);
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

    public void control(View view) {
        if (GameMessageManager.isConnected()) {
            Intent intent = new Intent(this, ControllerActivity.class);
            TextView name = findViewById(R.id.name);
            intent.putExtra(NAME, name.getText().toString());
            intent.putExtra(COLOR, String.valueOf(color));
            startActivity(intent);
        }
    }
}