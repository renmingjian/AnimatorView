package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.HistogramView
import java.util.*

class HistogramActivity : AppCompatActivity() {


    private lateinit var histogramView: HistogramView
    private val data = ArrayList<HistogramView.HistogramInfo>()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_histogram)

        histogramView = findViewById(R.id.histogramView)


        val random = Random()
        for (i in 0..19) {
            val info = HistogramView.HistogramInfo()
            val count = random.nextInt(31)
            info.count = count
//            info.count = 10 + i
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
