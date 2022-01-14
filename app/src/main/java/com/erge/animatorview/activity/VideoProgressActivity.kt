package com.erge.animatorview.activity

import android.animation.ObjectAnimator
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.TextView
import androidx.core.animation.addListener
import com.erge.animatorview.R
import com.erge.animatorview.view.VideoProgressBar
import com.erge.animatorview.view.VideoProgressLayout

class VideoProgressActivity : AppCompatActivity() {

    private lateinit var vpb: VideoProgressBar
    private lateinit var vpl: VideoProgressLayout
    private lateinit var tv_progress: TextView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_video_progress)
        vpb = findViewById(R.id.vpb)
        vpl = findViewById(R.id.vpl)
        tv_progress = findViewById(R.id.tv_progress)

        vpl.onDragProgressListener = {
            tv_progress.text = "进度：$it"
        }
    }

    fun play(view: android.view.View) {
        val objectAnimator = ObjectAnimator.ofFloat(0F, 1F)
        objectAnimator.addUpdateListener {
            vpb.seekTo(it.animatedValue as Float)
        }
        objectAnimator.duration = 5000
        objectAnimator.start()
    }

    fun play2(view: android.view.View) {
        val objectAnimator = ObjectAnimator.ofFloat(0F, 1F)
        objectAnimator.addUpdateListener {
            val progress = it.animatedValue as Float
            vpb.seekTo(progress, true)
        }
        objectAnimator.duration = 5000
        objectAnimator.start()
    }

    fun play3(view: android.view.View) {
        vpl.loading()

        val objectAnimator2 = ObjectAnimator.ofFloat(0F, 1F)
        objectAnimator2.addUpdateListener {
            val progress = it.animatedValue as Float
            vpl.seekTo(progress, false)
            tv_progress.text = "进度：$progress"
        }
        objectAnimator2.duration = 3000

        val objectAnimator = ObjectAnimator.ofFloat(0F, 1F)
        objectAnimator.addListener(onEnd = {
            objectAnimator2.start()
        })
        objectAnimator.duration = 3000
        objectAnimator.start()
    }
}