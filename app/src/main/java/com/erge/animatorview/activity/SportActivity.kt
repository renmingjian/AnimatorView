package com.erge.animatorview.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import com.erge.animatorview.R
import com.erge.animatorview.view.SportView

class SportActivity : AppCompatActivity() {

    private lateinit var sportView: SportView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sport)


        sportView = findViewById(R.id.sportView)

        val animator = ObjectAnimator.ofFloat(sportView, "progress", 0f, 1f)
        animator.duration = 2000
        animator.start()

    }
}
