package com.erge.animatorview.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import com.erge.animatorview.R
import com.erge.animatorview.view.ProgressLayoutView

class ProgressLayoutActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_progress_layout)

        anim()

        findViewById<Button>(R.id.btn).setOnClickListener { anim() }


    }


    private fun anim() {
        val progress: ProgressLayoutView = findViewById(R.id.progress)

        val animator = ObjectAnimator.ofFloat(progress, "progress", 0f, 1f)
        animator.duration = 5000
        animator.start()
    }
}
