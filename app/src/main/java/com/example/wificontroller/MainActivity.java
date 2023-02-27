package com.example.wificontroller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.View;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Arrays;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Button button = findViewById(R.id.connect);
        FileInputStream inputStream = null;
        File file = new File(getFilesDir(), "server");
        if (file.exists()) {
            try {
                inputStream = openFileInput("server");
            } catch (FileNotFoundException e) {
                throw new RuntimeException(e);
            }

            ArrayList<String> adresses = new ArrayList<String>();
            try {
                String content;
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                while ((content = bufferedReader.readLine()) != null) {
                    adresses.add(content);
                }
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, adresses);
            adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
            spinner.setAdapter(adapter);
        }

        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView textFromInput = findViewById(R.id.enter);
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        if (adressServer.equals("")){
                            GameMessageManager.connect(spinner.getSelectedItem().toString());
                        }
                        else {
                            GameMessageManager.connect(adressServer);
                        }
                        FileOutputStream outputStream = null;


                        ArrayList<String> adresses = new ArrayList<String>();
                        FileInputStream inputStream = null;
                        File file = new File(getFilesDir(), "server");
                        if (file.exists()) {
                            try {
                                inputStream = openFileInput("server");
                            } catch (FileNotFoundException e) {
                                throw new RuntimeException(e);
                            }
                            try {
                                String content;
                                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));
                                while ((content = bufferedReader.readLine()) != null) {
                                    adresses.add(content);
                                }
                            } catch (IOException e) {
                                throw new RuntimeException(e);
                            }
                        }

                        if (!file.exists()) {
                            FileOutputStream fileOutputStream = null;
                            try {
                                fileOutputStream = openFileOutput("server", Context.MODE_PRIVATE);
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
                                    fileOutputStream = openFileOutput("server", Context.MODE_APPEND);
                                    fileOutputStream.write((adressServer + System.getProperty("line.separator")).getBytes());
                                    fileOutputStream.close();
                                } catch (FileNotFoundException e) {
                                    throw new RuntimeException(e);
                                } catch (IOException e) {
                                    throw new RuntimeException(e);
                                }
                            }
                        }
                        control(v);
                        //GameMessageManager.sendMessage("MOTL=0.5");
                    }
                        /*else {
                            TextView errorText = findViewById(R.id.error);
                            errorText.setText("Une erreur est apparue lors de la connexion au serveur");
                        }*/
                });
    }

    public void control(View view) {
        Intent intent = new Intent(this, FirstController.class);
        startActivity(intent);
    }
}