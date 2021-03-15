package com.erge.animatorview.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.ImageView
import com.airbnb.lottie.LottieAnimationView
import com.airbnb.lottie.LottieDrawable
import com.erge.animatorview.R

class LottieActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_lottie)

        val iv_top: ImageView = findViewById(R.id.iv_top)

        val lottieView: LottieAnimationView = findViewById(R.id.lottieView)
        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                iv_top.visibility = View.GONE
                lottieView.visibility = View.VISIBLE
            }

            override fun onAnimationEnd(animation: Animator?, isReverse: Boolean) {
                super.onAnimationEnd(animation, isReverse)
                val drawable: LottieDrawable = lottieView.drawable as LottieDrawable
                println("drawable = $drawable")
//                iv_top.setImageDrawable(drawable)
                iv_top.visibility = View.VISIBLE
                lottieView.visibility = View.GONE
                val layoutParams = iv_top.layoutParams
//                layoutParams.width = lottieView.width
//                layoutParams.height = lottieView.height
//                iv_top.layoutParams = layoutParams
//                println("width = ${drawable.intrinsicWidth} --height = ${drawable.intrinsicHeight}")
//                println("frame = ${drawable.frame}")
            }
        })
    }
}