package com.erge.animatorview.activity

import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.erge.animatorview.R
import com.erge.animatorview.adapter.TestAdapter2
import com.erge.animatorview.view.HorizontalDragLayout

class HorizontalLoadMoreActivity : AppCompatActivity() {

    private lateinit var rvTest: RecyclerView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_horizontal_load_more)
        rvTest = findViewById(R.id.rv_test)
        rvTest.layoutManager = LinearLayoutManager(this, RecyclerView.HORIZONTAL, false)

        rvTest.adapter = TestAdapter2()

        val dragLayout: HorizontalDragLayout = findViewById(R.id.dragLayout)
//        dragLayout.setOnDragReleaseListener {
//            if (it == HorizontalDragLayout.State.OPEN) {
//                startActivity(Intent(this, TestActivity::class.java))
//            }
//        }
        dragLayout.setOnDragReleaseListener(object : HorizontalDragLayout.OnDragReleaseListener {
            override fun onRelease(state: HorizontalDragLayout.State?) {
                startActivity(Intent(this@HorizontalLoadMoreActivity, TestActivity::class.java))
            }
        })
    }
}