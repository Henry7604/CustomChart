package com.henry.chartdemo

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.henry.chart.ChartModel
import com.henry.chart.ChartView
import kotlin.random.Random

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val chartView = findViewById<ChartView>(R.id.chart)
        val list = ArrayList<ChartModel>()
        for (index in 0..999) {
            val model = ChartModel(Random.nextInt(0, 100))
            list.add(model)
        }
        chartView.setData(list)
    }
}