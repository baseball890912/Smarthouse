package com.example.kawamura.kotlinscraping

import android.app.Activity
import android.app.ProgressDialog
import android.widget.TextView
import android.os.AsyncTask
import android.util.Log
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import java.io.InputStream
import java.net.HttpURLConnection
import java.net.URL


/**
 * Created by kawamura on 2017/09/10.
 */

class Scraping(val mParentActivity: Activity, var mTextView: TextView) : AsyncTask<Void, Void, String>()  {

    private var mDialog: ProgressDialog? = null

    override fun onPreExecute() {
        mDialog = ProgressDialog(mParentActivity)
        mDialog!!.setMessage("通信中・・・・・・")
        mDialog!!.show()
    }

    override fun doInBackground(vararg arg0: Void): String {
        return exec_get()
    }

    override fun onPostExecute(string: String) {
        mDialog!!.dismiss()
        //this.mTextView.text = string
    }

    fun exec_get(): String {
        try {
            val url = "http://btcnews.jp/category/news/"
            val document: Document = Jsoup.connect(url).get()
            //val title: Elements = document.getElementsByTag("title")
            //val body: Elements = document.getElementsByTag("body")
            val a: Elements = document.getElementsByTag("a")
            Log.d("debug", "-----------------")
            a.forEach { Log.d("debug", it.toString()) }
            Log.d("debug", "-----------------")
            return a.toString()
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            return "ERROR"
        }
    }

}
