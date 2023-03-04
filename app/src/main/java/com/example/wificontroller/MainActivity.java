package com.example.wificontroller;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.PopupWindow;
import android.widget.Spinner;
import android.widget.TextView;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Objects;
import static java.lang.Thread.sleep;

import org.w3c.dom.Text;

public class MainActivity extends AppCompatActivity {

    public final static String COLOR = "com.example.wificontroller.COLOR";
    public static final String NAME = "com.example.wificontroller.NAME";


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle item selection
        switch (item.getItemId()) {
            case R.id.settings:
                showSettings();
                return true;
            case R.id.help:
                onButtonShowPopupWindowClick(new View(this));
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void onButtonShowPopupWindowClick(View view) {

        // inflate the layout of the popup window
        LayoutInflater inflater = (LayoutInflater)
                getSystemService(LAYOUT_INFLATER_SERVICE);
        View popupView = inflater.inflate(R.layout.activity_help, null);

        // create the popup window
        int width = LinearLayout.LayoutParams.WRAP_CONTENT;
        int height = LinearLayout.LayoutParams.WRAP_CONTENT;
        boolean focusable = true; // lets taps outside the popup also dismiss it
        final PopupWindow popupWindow = new PopupWindow(popupView, width, height, focusable);

        // show the popup window
        // which view you pass in doesn't matter, it is only used for the window tolken
        popupWindow.showAtLocation(view, Gravity.CENTER, 0, 0);

        // dismiss the popup window when touched
        popupView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                popupWindow.dismiss();
                return true;
            }
        });
    }

    private void showSettings() {
        Intent intent = new Intent(this, Setting.class);
        startActivity(intent);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Spinner spinner = (Spinner) findViewById(R.id.spinner);
        final Button button = findViewById(R.id.connect);

        ArrayList<String> adresses = lireFichier();
        ArrayList<String> adresseConcat = new ArrayList<>();
        for (String adresse : adresses){
            String[] adresses2 = adresse.split(",");
            adresseConcat.add(adresses2[0]+" | "+adresses2[1]+" | "+adresses2[2]);
        }
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.support_simple_spinner_dropdown_item, adresseConcat);
        adapter.setDropDownViewResource(R.layout.support_simple_spinner_dropdown_item);
        spinner.setAdapter(adapter);

        TextView textFromInput = findViewById(R.id.enter);
        TextView textFromInputName = findViewById(R.id.name);
        spinner.setOnItemSelectedListener(
                new AdapterView.OnItemSelectedListener(){
                    @Override
                    public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                        if ((adresses.size() == 1) || (spinner.getSelectedItem().toString().split(" | ")[0].equals("Choisissez"))){
                            textFromInput.setText("");
                            textFromInputName.setText("");
                        }
                        else {
                            textFromInput.setText(spinner.getSelectedItem().toString().split(" | ")[0]);
                            textFromInputName.setText(spinner.getSelectedItem().toString().split(" | ")[2]);
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
                        TextView textFromInputName = findViewById(R.id.name);
                        String name = textFromInputName.getText().toString();
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        GameMessageManager.connect(adressServer);
                        ecrireFichier(adressServer, name);
                        control();
                        //GameMessageManager.sendMessage("MOTL=0.5");
                    }
                });
    }



    public void control() {
        new Thread(new Runnable() {
            @Override
            public void run() {
                while (!GameMessageManager.isConnected()) {
                    try {
                        sleep(5);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
                runOnUiThread(new Runnable() {
                    @Override
                    public void run() {
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
        intent.putExtra(COLOR, String.valueOf(Setting.color));
        new Thread(new Runnable() {
            @Override
            public void run() {
                GameMessageManager.sendMessage("NAME=" + name.getText().toString() + "#COL=" + Setting.color + "#MSG=Salut");
            }
        }).start();
        startActivity(intent);
    }

    public ArrayList<String> lireFichier(){
        FileInputStream inputStream = null;
        File file = new File(getFilesDir(), "test3");
        ArrayList<String> adresses = new ArrayList<String>();
        adresses.add(0, "Choisissez dans votre historique, , ");
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

    public void ecrireFichier(String adressServer, String name) {
        File file = new File(getFilesDir(), "test3");
        ArrayList<String> adresses = lireFichier();
        String[] tableau = new String[]{adressServer, name, String.valueOf(Setting.color)};
        String chaine = String.join(",", tableau);
        FileOutputStream outputStream = null;
        if (!Objects.equals(adressServer, "")) {
            if (!file.exists()) {
                FileOutputStream fileOutputStream = null;
                try {
                    fileOutputStream = openFileOutput("test3", Context.MODE_PRIVATE);
                    fileOutputStream.write((chaine + System.getProperty("line.separator")).getBytes());
                    fileOutputStream.close();
                } catch (FileNotFoundException e) {
                    throw new RuntimeException(e);
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            } else {
                int dedans = 0;
                for (String adresse : adresses){
                    String[] adresse2 = adresse.split(",");
                    if (adresse2[0].equals(adressServer) && adresse2[1].equals(name) && adresse2[2].equals(String.valueOf(Setting.color))){
                        dedans++;
                    }
                }
                if (dedans==0) {
                    FileOutputStream fileOutputStream = null;
                    try {
                        fileOutputStream = openFileOutput("test3", Context.MODE_APPEND);
                        fileOutputStream.write((chaine + System.getProperty("line.separator")).getBytes());
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