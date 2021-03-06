package com.erge.animatorview.activity

import android.os.Bundle
import android.view.MotionEvent
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.erge.animatorview.R
import com.erge.animatorview.adapter.RVZoomAdapter
import com.erge.animatorview.bean.ZoomData

class ZoomActivity : AppCompatActivity() {

    private lateinit var recyclerView: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_zoom)

        recyclerView = findViewById(R.id.recyclerView)

        recyclerView.layoutManager = LinearLayoutManager(this)
        recyclerView.adapter = RVZoomAdapter(getData())
    }

    private fun getData(): MutableList<ZoomData> {
        return mutableListOf<ZoomData>().apply {
            for (i in 1..40) {
                val data = ZoomData(i % 3, "name--$i")
                add(data)
            }
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        return ZoomHelper.getInstance().dispatchTouchEvent(ev!!, this) || super.dispatchTouchEvent(
            ev
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        ZoomHelper.getInstance().release()
        ZoomHelper.getInstance().dismiss()
    }
}