package com.example.wificontroller;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {
    public static final String EXTRA_MESSAGE = "com.example.TP2_debut.goToController";
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        final Button button = findViewById(R.id.connect);
        button.setOnClickListener(
                new View.OnClickListener() {
                    public void onClick(View v) {
                        TextView textFromInput = findViewById(R.id.enter);
                        String adressServer = textFromInput.getText().toString();
                        GameMessageManager.logActivity(true);
                        if (adressServer.equals("192.168.1.98") || adressServer.equals("192.168.131.120")){
                            GameMessageManager.connect(adressServer);
                        }
                        //GameMessageManager.sendMessage("MOTL=0.5");
                    }
                        /*else {
                            TextView errorText = findViewById(R.id.error);
                            errorText.setText("Une erreur est apparue lors de la connexion au serveur");
                        }*/
                });
    }

    public void goToController(View view) {
        Intent intent = new Intent(this, FirstContrller.class);
        if (GameMessageManager.isConnected()){
            startActivity(intent);
        }
    }
}