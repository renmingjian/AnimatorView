package com.erge.animatorview.activity

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.os.Bundle
import android.view.View
import android.view.animation.AnimationUtils
import android.widget.FrameLayout
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.R
import com.erge.animatorview.view.TagLayout
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener

class BannerTestActivity : AppCompatActivity() {

    val list = mutableListOf<String>()
    val adapter = BannerTestAdapter(getData())

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_banner_test)

        val fl = findViewById<FrameLayout>(R.id.fl)
        fl.addView(TagLayout(this))





        val banner: Banner<String, BannerTestAdapter> = findViewById(R.id.banner)
        banner.isAutoLoop(false)
        banner.adapter = adapter

        banner.addOnPageChangeListener(object : OnPageChangeListener {
            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                println("listener-addOnPageChangeListener: $position")
            }

            override fun onPageSelected(position: Int) {
                println("listener-onPageSelected: $position")
            }

            override fun onPageScrollStateChanged(state: Int) {
                println("listener-onPageScrollStateChanged: $state")
            }
        })

        banner.setCurrentItem(0)


        val textView: TextView = findViewById(R.id.tv_test)
        val animator = ValueAnimator.ofInt(0 , 300)
        animator.addUpdateListener {
            val value = it.animatedValue as Int
            textView.layout(0, 0, value, 200)
        }
        animator.duration = 3000
        animator.start()
    }

    private fun getData(): MutableList<String> {
        return mutableListOf<String>().apply {
            add("https://img0.baidu.com/it/u=1505799353,2691569779&fm=26&fmt=auto&gp=0.jpg")
            add("https://img1.baidu.com/it/u=3032317551,3314409878&fm=26&fmt=auto&gp=0.jpg")
            add("https://img2.baidu.com/it/u=3576358313,3239146876&fm=26&fmt=auto&gp=0.jpg")
            add("https://img0.baidu.com/it/u=1563838440,2025992817&fm=11&fmt=auto&gp=0.jpg")
        }
    }

}