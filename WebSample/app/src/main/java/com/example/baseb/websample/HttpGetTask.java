package com.example.baseb.websample;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.widget.TextView;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.net.ssl.HttpsURLConnection;

/**
 * Created by baseb on 2017/04/25.
 */

public class HttpGetTask extends AsyncTask<Void,Void,String>{
    private TextView mTextView;
    private Activity mParentActivity;
    private ProgressDialog mDialog = null;

    private String mUri = "https://www.yamagiwalab.jp/~yama/KPK/Hello.html";

    public HttpGetTask(Activity parentActivity,TextView textView){
        this.mParentActivity = parentActivity;
        this.mTextView = textView;
    }

    @Override
    protected void onPreExecute(){
        mDialog = new ProgressDialog(mParentActivity);
        mDialog.setMessage("通信中・・・・・・");
        mDialog.show();
    }

    @Override
    protected String doInBackground(Void...arg0) {
        return exec_get();
    }

    @Override
    protected void onPostExecute(String string){
        mDialog.dismiss();
        this.mTextView.setText(string);
    }

    private String exec_get(){
        HttpURLConnection http = null;
        InputStream in = null;
        String src = "" ;
        try{
            URL url = new URL(mUri);
            http = (HttpURLConnection)url.openConnection();
            http.setRequestMethod("GET");
            http.connect();

            in = http.getInputStream();

            byte[] line = new byte[1024];
            int size;
            while (true){
                size = in.read(line);
                if (size <= 0){
                    break;
                }
                src += new String(line);
            }
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            try{
                if(http != null){
                    http.disconnect();
                }
                if(in != null) {
                    in.close();
                }
            }catch (Exception ignored){

            }
        }
        return src;
    }
}
