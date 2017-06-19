package com.example.kawamura.myapplication;

/**
 * Created by kawamura on 2017/06/19.
 */

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintStream;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLConnection;


public class HttpPost extends AsyncTask<Integer,Void,Void> {
    private Activity parentActivity;
    private ProgressDialog dialog = null;

    //private final String DEFAULTURL = "http://192.168.11.56/~pi/ledtest.php?";
    private final String DEFAULTURL = "http://192.168.0.11:3000/api/v1";
    private String uri = null;

    public HttpPost(Activity parentActivity){
        this.parentActivity = parentActivity;
    }

    @Override
    protected void onPreExecute() {
        dialog = new ProgressDialog(parentActivity);
        dialog.setMessage("Connecting...");
        dialog.show();
    }

    @Override
    protected Void doInBackground(Integer... params) {
        if (params[0] == 0){
            if (params[1] == 1) {
                uri = DEFAULTURL + "/tv/on";
            }
            else{
                uri = DEFAULTURL + "/tv/off";
            }
            exec_get();
            return null;
        }
        if (params[0] == 1){
            if (params[1] == 1) {
                uri = DEFAULTURL + "/aircon/on";
            }
            else{
                uri = DEFAULTURL + "/aircon/off";
            }
            exec_get();
            return null;
        }
        if (params[0] == 2){
            if (params[1] == 1) {
                uri = DEFAULTURL + "/bath/on";
            }
            else{
                uri = DEFAULTURL + "/bath/off";
            }
            exec_get();
            return null;
        }
        if (params[0] == 3){
            if (params[1] == 0) {
                uri = DEFAULTURL + "/projector/power";
            }
            else if (params[1] == 1) {
                uri = DEFAULTURL + "/projector/input";
            }
            else if (params[1] == 2) {
                uri = DEFAULTURL + "/projector/menu";
            }
            else if (params[1] == 3) {
                uri = DEFAULTURL + "/projector/enter";
            }
            else if (params[1] == 4) {
                uri = DEFAULTURL + "/projector/up";
            }
            else if (params[1] == 5) {
                uri = DEFAULTURL + "/projector/down";
            }
            else if (params[1] == 6) {
                uri = DEFAULTURL + "/projector/left";
            }
            else if (params[1] == 7) {
                uri = DEFAULTURL + "/projector/right";
            }
            else if (params[1] == 8) {
                uri = DEFAULTURL + "/projector/vol_u";
            }
            else if (params[1] == 9) {
                uri = DEFAULTURL + "/projector/vol_d";
            }
            exec_get();
            return null;
        }
        return null;
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        dialog.dismiss();
    }

    private String exec_get(){
        //HttpURLConnection http = null;
        //InputStream inputStream = null;
        String src = new String();
        try {
            URL url = new URL(uri);
            URLConnection uc = url.openConnection();
            uc.setDoOutput(true);//POST可能にする

            //uc.setRequestProperty("User-Agent", "@IT java-tips URLConnection");// ヘッダを設定
            //uc.setRequestProperty("Accept-Language", "ja");// ヘッダを設定
            OutputStream os = uc.getOutputStream();//POST用のOutputStreamを取得

            //OutputStreamWriter ow1 = new OutputStreamWriter(uc.getOutputStream());
            //BufferedWriter bw1 = new BufferedWriter(ow1);

            //String postStr = "foo1=bar1&foo2=bar2";//POSTするデータ
            //PrintStream ps = new PrintStream(os);
            //ps.print(postStr);//データをPOSTする
            //ps.close();

            InputStream is = uc.getInputStream();//POSTした結果を取得
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            String s;
            while ((s = reader.readLine()) != null) {
                System.out.println(s);
                src += s;
            }
            reader.close();
        } catch (MalformedURLException e) {
            System.err.println("Invalid URL format: " + uri);
            System.exit(-1);
        } catch (IOException e) {
            System.err.println("Can't connect to " + uri);
            System.exit(-1);
        } catch (Exception e){
            System.out.println("########################");
            System.out.println(e);
        }
        return src;
    }
}