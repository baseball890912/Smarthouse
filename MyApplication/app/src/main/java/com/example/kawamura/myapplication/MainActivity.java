package com.example.kawamura.myapplication;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        Button sendButton = (Button) findViewById(R.id.send_button);
        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), tv.class);
                startActivity(intent);
            }
        });

        Button sendButton1 = (Button) findViewById(R.id.send_button1);
        sendButton1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), aircon.class);
                startActivity(intent);
            }
        });

        Button sendButton2 = (Button) findViewById(R.id.send_button2);
        sendButton2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), bath.class);
                startActivity(intent);
            }
        });

        Button sendButton3 = (Button) findViewById(R.id.send_button3);
        sendButton3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), projector.class);
                startActivity(intent);
            }
        });

        Button sendButton4 = (Button) findViewById(R.id.send_button4);
        sendButton4.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplication(), temp.class);
                startActivity(intent);
            }
        });
    }
}
