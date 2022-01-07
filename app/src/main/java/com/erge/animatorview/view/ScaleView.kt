package com.erge.animatorview.view

import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View

/**
 * Created by eryemj 2021/8/15
 */
class ScaleView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    val paint: Paint = Paint(Paint.ANTI_ALIAS_FLAG);
    var viewWidth = 0
    val rectList = mutableListOf<RectF>()
    var shortLineHeight = 0
    var longLineHeight = 0
    var scaleMargin = 0
    var downX = 0f
    var moveX = 0f
    var upX = 0f

    init {
        paint.color = Color.parseColor("#88000000")
        paint.style = Paint.Style.STROKE
        paint.strokeWidth = 4f
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        viewWidth = width
        shortLineHeight = height * 12 / 70
        longLineHeight = height * 40 / 70
        scaleMargin = (width - 43 * 4) / 42
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        canvas.restore()
    }

    private fun drawLines(canvas: Canvas) {

    }

    private fun calculateCoordinator() {

    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        when (event.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                moveX = 0f
                upX = 0f
            }
            MotionEvent.ACTION_MOVE -> {
                moveX = event.x
                invalidate()
            }
            MotionEvent.ACTION_UP -> {
                upX = event.x
                invalidate()
            }
        }
        return true
    }

}