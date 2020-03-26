package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.OvalView
import com.erge.animatorview.view.VerticalScrollView

class ScrollActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_scroll)

        val verticalScrollView = findViewById<VerticalScrollView>(R.id.scrollView)
        val viewOval = findViewById<OvalView>(R.id.viewOval)
        verticalScrollView.setOnScrollListener { _, _, alpha, up ->
            {
                println("alpha = " + alpha)
                if (alpha === 1f) {
                    viewOval.switchColor(up)
                }
            }
        }
    }
}
