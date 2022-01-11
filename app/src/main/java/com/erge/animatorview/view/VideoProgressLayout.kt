package com.erge.animatorview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import com.erge.animatorview.R

/**
 * Created by eryemj 2022/1/11
 */
class VideoProgressLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private var loadingView: VideoLoadingBar
    private var progressView: VideoProgressBar
    var onDragProgressListener: ((progress: Float) -> Unit)? = null
        set(value) {
            field = value
            progressView.onDragProgressListener = value
        }

    init {
        val rootLayout =
            LayoutInflater.from(context).inflate(R.layout.layout_video_progress, this, true)
        loadingView = rootLayout.findViewById(R.id.vb)
        progressView = rootLayout.findViewById(R.id.vpb)
    }

    fun loading() {
        loadingView.visibility = View.VISIBLE
        progressView.visibility = View.GONE
    }

    fun seekTo(to: Float, showCircle: Boolean = false) {
        loadingView.visibility = View.GONE
        progressView.visibility = View.VISIBLE
        progressView.seekTo(to, showCircle)
    }

}