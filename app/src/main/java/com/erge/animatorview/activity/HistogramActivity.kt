package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.HistogramView
import java.util.ArrayList

class HistogramActivity : AppCompatActivity() {


    private lateinit var histogramView: HistogramView
    private val data = ArrayList<HistogramView.HistogramInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histogram)

        histogramView = findViewById(R.id.histogramView)


        for (i in 0..19) {
            val info = HistogramView.HistogramInfo()
            info.count = 10 + i
            info.time = if (i >= 10) "07-" + (i + 1) else "07-0$i"

            data.add(info)
        }

    }

    override fun onResume() {
        super.onResume()

        histogramView.postDelayed({
            println("onSizeChanged = set")
            histogramView.setData(data)
        }, 300)
    }
}
