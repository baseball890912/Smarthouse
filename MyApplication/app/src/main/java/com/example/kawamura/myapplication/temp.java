package com.example.kawamura.myapplication;

import android.app.Activity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

/**
 * Created by kawamura on 2017/06/19.
 */

public class temp extends Activity implements View.OnClickListener{

    private TextView mReturnTextView;
    private TextView mReturnTextView1;

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.temp);

        Button returnButton = (Button) findViewById(R.id.return_button);
        returnButton.setOnClickListener(new View.OnClickListener() {@Override public void onClick(View v) {finish();}});

        mReturnTextView = (TextView) findViewById(R.id.text_view_return);
        mReturnTextView1 = (TextView) findViewById(R.id.text_view_return1);

        Button temp = (Button)findViewById(R.id.button);
        temp.setOnClickListener(this);
        Button atom = (Button)findViewById(R.id.button1);
        atom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        Log.d("WebSample","wlog onClick()");
        if (v.getId() == R.id.button){
            HttpGet task = new HttpGet(this,mReturnTextView,"http://192.168.0.11/~pi/test.php");
            task.execute();
        }
        else if (v.getId() == R.id.button1){
            HttpGet task = new HttpGet(this,mReturnTextView1,"http://192.168.0.11/~pi/test100.txt");
            task.execute();
        }
    }
}
