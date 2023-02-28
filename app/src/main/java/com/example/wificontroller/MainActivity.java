package com.example.wificontroller;

import com.pes.androidmaterialcolorpickerdialog.ColorPicker;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;
import com.pes.androidmaterialcolorpickerdialog.ColorPickerCallback;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Objects;
import static java.lang.Thread.sleep;

public class MainActivity extends AppCompatActivity {

    public final static String COLOR = "com.example.wificontroller.COLOR";
    public static final String NAME = "com.example.wificontroller.NAME";
    int color = 4651321;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        initializeColorPicker();

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Button button = findViewById(R.id.connect);

        ArrayList<String> adresses = lireFichier();
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, adresses);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView textFromInput = findViewById(R.id.enter);

        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if ((adresses.size() == 1) || (spinner.getSelectedItem().toString().equals("Choisissez dans votre historique"))){
                            textFromInput.setText("");
                        }
                        else {
                            textFromInput.setText(spinner.getSelectedItem().toString());
                        }
                    }

                    @Override
                    public void onNothingSelected(AdapterView<?> adapterView) {

                    }
                }
        );

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView textFromInput = findViewById(R.id.enter);
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        GameMessageManager.connect(adressServer);
                        ecrireFichier(adressServer);
                        control();
                        //GameMessageManager.sendMessage("MOTL=0.5");
                    }
                });
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

    public void control() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!GameMessageManager.isConnected()) {
                    Log.i("cbot", "Waiting for connection");
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
                        Log.i("cbot","launchng");
                        game();
                    }
                });
            }
        }).start();
    }

    public void game() {
        Intent intent = new Intent(this, ControllerActivity.class);
        TextView name = findViewById(R.id.name);
        intent.putExtra(NAME, name.getText().toString());
        intent.putExtra(COLOR, String.valueOf(color));
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameMessageManager.sendMessage("NAME=" + name.getText().toString() + "#COL=" + color + "#MSG=Salut");
            }
        }).start();
        startActivity(intent);
    }

    public ArrayList<String> lireFichier(){
        FileInputStream inputStream = null;
        File file = new File(getFilesDir(), "test3");
        ArrayList<String> adresses = new ArrayList<String>();
        adresses.add(0, "Choisissez dans votre historique");
        if (file.exists()) {
            try {
                inputStream = openFileInput("test3");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            try {
                String content;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((content = bufferedReader.readLine()) != null) {
                    adresses.add(1,content);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
        return adresses;
    }

    public void ecrireFichier(String adressServer) {
        File file = new File(getFilesDir(), "test3");
        ArrayList<String> adresses = lireFichier();
        FileOutputStream outputStream = null;
        if (!Objects.equals(adressServer, "")) {
            if (!file.exists()) {
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput("test3", Context.MODE_PRIVATE);
                    fileOutputStream.write((adressServer + System.getProperty("line.separator")).getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                if (!adresses.contains(adressServer)) {
                    Log.i("adresses", adresses.toString());
                    Log.i("add", adressServer);
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = openFileOutput("test3", Context.MODE_APPEND);
                        fileOutputStream.write((adressServer + System.getProperty("line.separator")).getBytes());
                        fileOutputStream.close();
                    } catch (FileNotFoundException e) {
                        throw new RuntimeException(e);
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }
                }
            }
        }
    }
}