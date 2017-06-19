package com.example.kawamura.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.ToggleButton;

/**
 * Created by kawamura on 2017/06/19.
 */

public class bath extends Activity implements CompoundButton.OnCheckedChangeListener{
    private ToggleButton ledToggleButton1;
    private int stat = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.bath);

        Button returnButton = (Button) findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {finish();}});

        ledToggleButton1 = (ToggleButton) findViewById(R.id.ledToggleButton);
        ledToggleButton1.setOnCheckedChangeListener(this);
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        if (isChecked == true) {
            stat = 1;
        } else {
            stat = 0;
        }

        HttpPost task = new HttpPost(this);
        task.execute(2, stat);
    }
}
