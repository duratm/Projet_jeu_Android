package com.example.wificontroller;

import android.content.Intent;
import android.view.View;
import android.widget.FrameLayout;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

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
                        GameMessageManager.connect(adressServer);
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