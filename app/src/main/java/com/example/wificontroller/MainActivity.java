package com.example.wificontroller;

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
import java.util.Objects;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

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
                        //TextView textFromInput = findViewById(R.id.enter);
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        GameMessageManager.connect(adressServer);
                        ecrireFichier(adressServer);
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