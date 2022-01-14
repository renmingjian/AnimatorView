package com.erge.animatorview.view

import android.content.Context
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.MotionEvent
import android.view.View
import androidx.core.view.GestureDetectorCompat
import kotlin.math.abs

/**
 * Created by eryemj 2022/1/14
 */
class VideoDragView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val gestureDetector: GestureDetectorCompat

    private var downX = 0F
    private var downY = 0F
    private var offset = 0F
    private var progress = 0F
    private var direction = PUSH

    // 点击事件
    var onVideoClickListener: (() -> Unit)? = null

    // 长按事件
    var onVideoLongClickListener: (() -> Unit)? = null

    // 双击事件
    var onVideoDoubleClickListener: (() -> Unit)? = null

    // 滑动事件：progress->滑动的距离/屏幕宽度；dragDirection->前进还是后退
    var onVideoDragListener: ((progress: Float, dragDirection: Int) -> Unit)? = null

    // 滑动结束事件
    var onVideoDragUPListener: ((progress: Float, dragDirection: Int) -> Unit)? = null


    companion object {
        // 拖动视频是前进还是后退
        const val PUSH = 0
        const val BACK = 1
    }

    init {
        gestureDetector =
            GestureDetectorCompat(context, object : GestureDetector.SimpleOnGestureListener() {

                override fun onDown(e: MotionEvent?): Boolean {
                    offset = 0F
                    progress = 0F
                    return true
                }

                override fun onSingleTapConfirmed(e: MotionEvent?): Boolean {
                    onVideoClickListener?.invoke()
                    return super.onSingleTapConfirmed(e)
                }

                override fun onScroll(
                    e1: MotionEvent?,
                    e2: MotionEvent?,
                    distanceX: Float,
                    distanceY: Float
                ): Boolean {
                    offset += distanceX
                    direction = if (offset > 0) BACK else PUSH
                    progress = -(offset / width.toFloat())
                    onVideoDragListener?.invoke(progress, direction)
                    return super.onScroll(e1, e2, distanceX, distanceY)
                }

                override fun onLongPress(e: MotionEvent?) {
                    onVideoLongClickListener?.invoke()
                    super.onLongPress(e)
                }

                override fun onDoubleTap(e: MotionEvent?): Boolean {
                    onVideoDoubleClickListener?.invoke()
                    return false
                }

                override fun onDoubleTapEvent(e: MotionEvent?): Boolean {
                    return false
                }
            })

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        var handleEvent = true
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
            }
            MotionEvent.ACTION_MOVE -> {
                val moveX = event.x
                val moveY = event.y
                handleEvent = abs(moveX - downX) > abs(moveY - downY)
            }
            MotionEvent.ACTION_UP -> {
                onVideoDragUPListener?.invoke(progress, direction)
            }
        }
        return handleEvent && gestureDetector.onTouchEvent(event)
    }
}