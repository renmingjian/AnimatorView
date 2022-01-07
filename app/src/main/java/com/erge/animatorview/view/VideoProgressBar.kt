package com.erge.animatorview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by eryemj 2022/1/7
 */
class VideoProgressBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectFBg = RectF()
    private val rectF = RectF()
    private var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        drawBg(canvas)
        drawProgress(canvas)
        canvas?.restore()
    }

    private fun drawBg(canvas: Canvas?) {

    }

    private fun drawProgress(canvas: Canvas?) {

    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                progress = event.x / width
                if (progress < 0f) progress = 0f
                if (progress > 1f) progress = 1f
                invalidate()
            }
        }
        return true
    }

}