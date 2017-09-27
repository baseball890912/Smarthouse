package com.example.kawamura.kotlinscraping

/**
 * Created by kawamura on 2017/09/25.
 */
import android.app.Activity
import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.view.View
import com.github.mikephil.charting.charts.LineChart
import com.github.mikephil.charting.data.Entry
import com.github.mikephil.charting.data.LineData
import com.github.mikephil.charting.data.LineDataSet

import java.util.ArrayList

/**
 * Created by hp on 2016/08/14.
 */

class GraphLineActivity : Activity() {

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
                val intent = Intent(application, NewsActivity::class.java)
                startActivity(intent)
                return true
            }
            R.id.item4 -> {
                val intent = Intent(application, MarketActivity::class.java)
                startActivity(intent)
                return true
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.graph)

        val lineChart = findViewById<View>(R.id.chart1) as LineChart

        //グラフ用データ
        val entries = ArrayList<Entry>()
        entries.add(Entry(60f, 0))
        entries.add(Entry(50f, 1))
        entries.add(Entry(58f, 2))
        entries.add(Entry(60f, 3))
        entries.add(Entry(65f, 4))
        entries.add(Entry(80f, 5))
        entries.add(Entry(78f, 6))

        //データをセット
        val dataSet = LineDataSet(entries, "weight")

        //ラベル
        val labels = arrayOf("2015", "2016", "2017", "2018", "2019", "2020", "2021")

        //LineDataインスタンス生成
        val data = LineData(labels, dataSet)

        //LineDataをLineChartにセット
        lineChart.data = data

        //説明分
        lineChart.setDescription("体重の遷移")

        //背景色
        lineChart.setBackgroundColor(Color.WHITE)

        //アニメーション
        lineChart.animateX(1200)

    }
}