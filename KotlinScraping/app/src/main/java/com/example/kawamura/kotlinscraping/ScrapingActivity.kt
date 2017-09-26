package com.example.kawamura.kotlinscraping

/**
 * Created by kawamura on 2017/09/26.
 */
import android.app.ProgressDialog
import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.os.AsyncTask;
import android.util.Log;
import android.view.Menu
import android.view.MenuItem
import org.jsoup.Jsoup
import org.jsoup.nodes.Document
import org.jsoup.select.Elements
import java.io.IOException
import android.widget.ArrayAdapter
import android.widget.ListView


class ScrapingActivity : AppCompatActivity() {

    override fun onCreateOptionsMenu(menu: Menu): Boolean {
        //main.xmlの内容を読み込む
        val inflater = menuInflater
        inflater.inflate(R.menu.main, menu)
        return true
    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        when (item.getItemId()) {
            R.id.item1 -> {
                val intent = Intent(application, MainActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item2 -> {
                val intent = Intent(application, GraphLineActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item3 -> {
                val intent = Intent(application, ScrapingActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    private var mAdapter: ArrayAdapter<String>? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.listview)

        //val listView = ListView(this)
        //setContentView(listView)
        Log.d("debug", "-----------------")

        this.mAdapter = ArrayAdapter<String>(this, android.R.layout.simple_list_item_1)
        val lv = this.findViewById(R.id.listView) as ListView
        lv.setAdapter(this.mAdapter)

        Log.d("debug", "-----------------")

        startAsync()

    }

    fun startAsync() {
        val testTask = TestTask(this)
        // executeを呼んでAsyncTaskを実行する、パラメータは１番目
        testTask.execute()
    }

    fun setListView(elements: Elements?) {
        // AsyncTaskからのカウントを受け取り表示する
        // simple_list_item_1 は、 もともと用意されている定義済みのレイアウトファイルのID
        val adapter = this.mAdapter
        // リストビューに追加する
        for (element in elements!!) {
            adapter!!.add(element.toString())
        }
        /*
        // 昇順にソートする
        adapter!!.sort(object : Comparator<String> {
            override fun compare(lhs: String, rhs: String): Int {
                return lhs.compareTo(rhs)
            }
        })
        */
    }
}

class TestTask(private val mParentActivity: ScrapingActivity) : AsyncTask<Int, Int, Elements>() {

    private var mDialog: ProgressDialog? = null

    override fun onPreExecute() {
        mDialog = ProgressDialog(mParentActivity)
        mDialog!!.setMessage("通信中・・・・・・")
        mDialog!!.show()
    }

    // 非同期処理
    override fun doInBackground(vararg p0: Int?): Elements? {
        var result:Elements? = null
        try {
            val url = "http://btcnews.jp/category/news/"
            val document: Document = Jsoup.connect(url).get()
            //val title: Elements = document.getElementsByTag("title")
            //val body: Elements = document.getElementsByTag("body")
            val a: Elements = document.getElementsByTag("a")
            Log.d("debug", "-----------------")
            a.forEach { Log.d("debug", it.toString()) }
            Log.d("debug", "-----------------")
            result = a
        } catch (e: IOException) {
            // TODO Auto-generated catch block
            e.printStackTrace()
            //return "ERROR"
        }
        return result
    }

    // 途中経過をメインスレッドに返す
    //protected override fun onProgressUpdate(vararg values: Int?) {
        //progressDialog_.incrementProgressBy(progress[0]);
        //mParentActivity.setTextView(values[0])
    //}

    // 非同期処理が終了後、結果をメインスレッドに返す
    override fun onPostExecute(result: Elements?) {
        mDialog!!.dismiss()
        mParentActivity.setListView(result)
    }
}

