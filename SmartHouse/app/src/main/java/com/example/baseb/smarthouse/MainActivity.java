package com.example.baseb.smarthouse;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.CompoundButton;
import android.widget.CompoundButton.OnCheckedChangeListener;
import android.widget.TextView;
import android.widget.ToggleButton;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    private TextView mReturnTextView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mReturnTextView = (TextView) findViewById(R.id.text_view_return);

        Button button = (Button)findViewById(R.id.button);
        button.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("WebSample","wlog onClick()");
        if (v.getId() == R.id.button){
            HttpGetTask task = new HttpGetTask(this,mReturnTextView);
            task.execute();
        }
    }
}
