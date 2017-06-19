package com.example.kawamura.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by kawamura on 2017/06/19.
 */

public class projector extends Activity implements CompoundButton.OnCheckedChangeListener, View.OnClickListener {

    private ToggleButton ledToggleButton1;
    //private int stat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.projector);

        Button returnButton = (Button) findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {finish();}});

        ledToggleButton1 = (ToggleButton) findViewById(R.id.ledToggleButton);
        ledToggleButton1.setOnCheckedChangeListener(this);

        Button b1 = (Button)findViewById(R.id.button1);
        b1.setOnClickListener(this);
        Button b2 = (Button)findViewById(R.id.button2);
        b2.setOnClickListener(this);
        Button b3 = (Button)findViewById(R.id.button3);
        b3.setOnClickListener(this);
        Button b4 = (Button)findViewById(R.id.button4);
        b4.setOnClickListener(this);
        Button b5 = (Button)findViewById(R.id.button5);
        b5.setOnClickListener(this);
        Button b6 = (Button)findViewById(R.id.button6);
        b6.setOnClickListener(this);
        Button b7 = (Button)findViewById(R.id.button7);
        b7.setOnClickListener(this);
        Button b8 = (Button)findViewById(R.id.button8);
        b8.setOnClickListener(this);
        Button b9 = (Button)findViewById(R.id.button9);
        b9.setOnClickListener(this);


    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        HttpPost task = new HttpPost(this);
        task.execute(3, 0);
    }

    @Override
    public void onClick(View v) {
        Log.d("WebSample","wlog onClick()");
        if (v.getId() == R.id.button1){
            HttpPost task = new HttpPost(this);
            task.execute(3, 4);
        }
        else if (v.getId() == R.id.button2){
            HttpPost task = new HttpPost(this);
            task.execute(3, 5);
        }
        else if (v.getId() == R.id.button3){
            HttpPost task = new HttpPost(this);
            task.execute(3, 7);
        }
        else if (v.getId() == R.id.button4){
            HttpPost task = new HttpPost(this);
            task.execute(3, 6);
        }
        else if (v.getId() == R.id.button5){
            HttpPost task = new HttpPost(this);
            task.execute(3, 9);
        }
        else if (v.getId() == R.id.button6){
            HttpPost task = new HttpPost(this);
            task.execute(3, 8);
        }
        else if (v.getId() == R.id.button7){
            HttpPost task = new HttpPost(this);
            task.execute(3, 2);
        }
        else if (v.getId() == R.id.button8){
            HttpPost task = new HttpPost(this);
            task.execute(3, 1);
        }
        else if (v.getId() == R.id.button9){
            HttpPost task = new HttpPost(this);
            task.execute(3, 3);
        }
    }
}
