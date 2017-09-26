package com.example.kawamura.kotlinscraping

import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.content.Intent
import android.view.Menu
import android.view.MenuItem


class MainActivity : AppCompatActivity() , View.OnClickListener {
    private var mReturnTextView: TextView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        //val button = findViewById(R.id.button) as Button
        //button.setOnClickListener(this)

        //mReturnTextView = findViewById(R.id.textView1) as TextView

        //val graph = findViewById(R.id.graph) as Button
        //graph.setOnClickListener(this)
    }


    override fun onClick(v: View) {
        if (v.id === R.id.button) {
            val scraping = Scraping(this,mReturnTextView!!)
            scraping.execute()
        }
        else if (v.id === R.id.graph){
            val intent = Intent(application, GraphLineActivity::class.java)
            startActivity(intent)
        }
    }


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
}
