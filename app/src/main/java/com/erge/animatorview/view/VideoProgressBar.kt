package com.erge.animatorview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.MotionEvent
import android.view.View
import com.erge.animatorview.R

/**
 * Created by eryemj 2022/1/7
 */
class VideoProgressBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val paintBg = Paint(Paint.ANTI_ALIAS_FLAG)
    private val rectFBg = RectF()
    private val rectF = RectF()
    private var lineHeight: Float = 0F
    private var circleRadius: Float = 0F
    private var color: Int = Color.parseColor("#FFFFFFFF")
    private var colorBg: Int = Color.parseColor("#FFFFFFFF")
    var onDragProgressListener: ((progress: Float) -> Unit)? = null
    var showCircle = false
        set(value) {
            field = value
            invalidate()
        }
    var progress = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        val ta = context.obtainStyledAttributes(attributeSet, R.styleable.VideoProgressBar)
        lineHeight = ta.getDimension(R.styleable.VideoProgressBar_progress_line_height, 0F)
        circleRadius = ta.getDimension(R.styleable.VideoProgressBar_progress_circle_radius, 0F)
        color = ta.getColor(R.styleable.VideoProgressBar_progress_color, color)
        colorBg = ta.getColor(R.styleable.VideoProgressBar_progress_line_bg_color, color)
        ta.recycle()

        paintBg.color = colorBg
        paint.color = color
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val widthSize = MeasureSpec.getSize(widthMeasureSpec)
        val widthMode = MeasureSpec.getMode(widthMeasureSpec)
        val heightSize = MeasureSpec.getSize(heightMeasureSpec)
        val heightMode = MeasureSpec.getMode(heightMeasureSpec)
        if (widthMode != MeasureSpec.AT_MOST && heightMode != MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec)
        } else {
            var needHeightSize = heightSize
            if (heightMeasureSpec == MeasureSpec.AT_MOST) {
                needHeightSize = (circleRadius * 2).toInt()
            }
            setMeasuredDimension(widthSize, needHeightSize)
        }
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        canvas?.translate(0F, height / 2F)
        drawBg(canvas)
        drawProgress(canvas)
        println("showCircle = $showCircle")
        if (showCircle) {
            drawProgressCircle(canvas)
        }
        canvas?.restore()
    }

    private fun drawBg(canvas: Canvas?) {
        rectFBg.left = 0F
        rectFBg.top = -lineHeight / 2F
        rectFBg.right = width.toFloat()
        rectFBg.bottom = lineHeight / 2F
        canvas?.drawRoundRect(rectFBg, 0F, 0F, paintBg)
    }

    private fun drawProgress(canvas: Canvas?) {
        rectF.left = 0F
        rectF.top = -lineHeight / 2F
        rectF.right = width * progress
        rectF.bottom = lineHeight / 2F
        canvas?.drawRoundRect(rectF, 0F, 0F, paint)
    }

    private fun drawProgressCircle(canvas: Canvas?) {
        val rx = (width - circleRadius * 2) * progress + circleRadius
        val ry = 0F
        canvas?.drawCircle(rx, ry, circleRadius, paint)
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        when (event?.action) {
            MotionEvent.ACTION_DOWN, MotionEvent.ACTION_MOVE -> {
                showCircle = true
                progress = event.x / width
                if (progress < 0f) progress = 0f
                if (progress > 1f) progress = 1f
                onDragProgressListener?.invoke(progress)
            }
            MotionEvent.ACTION_UP -> {
                showCircle = false
            }
        }
        invalidate()
        return true
    }

    fun seekTo(to: Float, showCircle: Boolean = false) {
        this.showCircle = showCircle
        this.progress = to
        invalidate()
    }

}