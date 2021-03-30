package com.erge.animatorview.view

import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.AttributeSet
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.MotionEvent
import android.view.View
import android.view.animation.LinearInterpolator
import com.erge.animatorview.R

/**
 * Created by erge 2019-08-28 17:21
 */
class GradientButton(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mState = State.NORMAL
    private var mWidth = 0
    private var mHeight = 0
    private var mRadius = 0f

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDetector: GestureDetector
    var text: String? = null
    var textColor: Int? = null
    var textSize: Float? = null
    var fillColor: Int? = null
    var cornerSize: Float? = null

    var gradientStyle: Int = 1
    var onLoadingListener: () -> Unit = {}

    private var lineProgress = 1f
        set(value) {
            field = value
            invalidate()
        }
    private var circleProgress = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var textAlpha = 255
        set(value) {
            field = value
            invalidate()
        }

    enum class State {
        NORMAL,
        NORMAL_TO_LOADING,
        LOADING,
        COMPLETE,
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton)
        text = typedArray.getString(R.styleable.LoadingButton_text)
        gradientStyle = typedArray.getInt(R.styleable.LoadingButton_gradientStyle, 1)
        textColor =
            typedArray.getColor(R.styleable.LoadingButton_text_color, Color.parseColor("#ffffff"))
        textSize = typedArray.getDimension(R.styleable.LoadingButton_text_size, 14f)
        fillColor =
            typedArray.getColor(R.styleable.LoadingButton_fill_color, Color.parseColor("#ffd700"))
        cornerSize = typedArray.getDimension(R.styleable.LoadingButton_corner_size, 0f)
        typedArray.recycle()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width = MeasureSpec.getSize(widthMeasureSpec)
        val height = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onTouchEvent(event: MotionEvent): Boolean {
        return mDetector.onTouchEvent(event)
    }

    override fun onSizeChanged(w: Int, h: Int, oldw: Int, oldh: Int) {
        super.onSizeChanged(w, h, oldw, oldh)
        mWidth = width
        mHeight = height
        val min = width.coerceAtMost(height)
        mRadius = (min shr 1).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mState) {
            State.NORMAL -> drawButton(canvas)
            State.NORMAL_TO_LOADING -> drawGradientButton(canvas)
            State.LOADING -> drawLoading(canvas)
            else -> {}
        }
    }

    private fun drawButton(canvas: Canvas) {
        canvas.save()
        mPaint.color = fillColor!!
        mPaint.style = Paint.Style.FILL
        canvas.drawRoundRect(
            0f,
            0f,
            mWidth.toFloat(),
            mHeight.toFloat(),
            cornerSize!!,
            cornerSize!!,
            mPaint
        )
        drawText(canvas)
        canvas.restore()
    }

    private fun drawGradientButton(canvas: Canvas) {
        mPaint.color = fillColor!!
        mPaint.style = Paint.Style.FILL
        var leftCircleLeft = 0f
        var middleRectLeft = 0f
        var rightCircleLeft = 0f
        val top = 0f
        var leftCircleRight = 0f
        var middleRectRight = 0f
        var rightCircleRight = 0f
        val bottom = mHeight.toFloat()
        when (gradientStyle) {
            0 -> {
                leftCircleLeft = 0f
                leftCircleRight = mRadius * 2
                middleRectLeft = mRadius
                middleRectRight = (mWidth - 2 * mRadius) * lineProgress + mRadius
                rightCircleLeft = (mWidth - 2 * mRadius) * lineProgress
                rightCircleRight = rightCircleLeft + 2 * mRadius
            }
            1 -> {
                leftCircleLeft = (mRadius - mWidth / 2) * (lineProgress - 1)
                leftCircleRight = leftCircleLeft + mRadius * 2
                middleRectLeft = (mRadius - mWidth / 2) * lineProgress + mWidth / 2
                middleRectRight = (mWidth / 2 - mRadius) * lineProgress + mWidth / 2
                rightCircleLeft = (mWidth / 2 - mRadius) * (lineProgress + 1)
                rightCircleRight = rightCircleLeft + mRadius * 2
            }
            2 -> {
                leftCircleLeft = (2 * mRadius - mWidth) * (lineProgress - 1)
                leftCircleRight = leftCircleLeft + 2 * mRadius
                middleRectLeft = (2 * mRadius - mWidth) * lineProgress + mWidth - mRadius
                middleRectRight = mWidth - mRadius
                rightCircleLeft = mWidth - 2 * mRadius
                rightCircleRight = mWidth.toFloat()
            }
        }

        // 左侧半圆
        canvas.drawArc(
            leftCircleLeft, top, leftCircleRight, bottom, 90f, 180f,
            false, mPaint
        )
        // 中间矩形
        canvas.drawRect(middleRectLeft, top, middleRectRight, bottom, mPaint)
        // 右侧半圆
        canvas.drawArc(
            rightCircleLeft, top, rightCircleRight, bottom, 270f,
            180f, false, mPaint
        )
    }

    private fun drawText(canvas: Canvas) {
        // 画文字
        if (!TextUtils.isEmpty(text)) {
            mPaint.textSize = textSize!!
            mPaint.color = textColor!!
            mPaint.textAlign = Paint.Align.CENTER
            val metrics = Paint.FontMetrics()
            mPaint.getFontMetrics(metrics)
            mPaint.alpha = textAlpha
            val offset = (metrics.ascent + metrics.descent) / 2
            val baseLine = (mHeight shr 1) - offset
            canvas.drawText(text!!, (mWidth shr 1).toFloat(), baseLine, mPaint)
        }
    }

    private fun drawLoading(canvas: Canvas) {
        canvas.save()
        var radiusX = 0f
        val radiusY = mHeight / 2.toFloat()
        when (gradientStyle) {
            0 -> radiusX = mRadius
            1 -> radiusX = mWidth / 2.toFloat()
            2 -> radiusX = mWidth - mRadius
        }
        mPaint.color = fillColor!!
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(radiusX, radiusY, mRadius, mPaint)
        canvas.restore()
    }


    private fun startAlpha() {
        val animator = ObjectAnimator.ofInt(this, "textAlpha", 255, 0)
        animator.interpolator = LinearInterpolator()
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Int
            if (value == 0) {
                startGradientAnim()
            }
        }
        animator.duration = 2000
        animator.start()
    }

    /**
     * 开启渐变为圆的效果
     */
    private fun startGradientAnim() {
        state = State.NORMAL_TO_LOADING
        val animator = ObjectAnimator.ofFloat(this, "lineProgress", 1f, 0f)
        animator.interpolator = LinearInterpolator()
        animator.duration = 250
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            if (value == 0f) {
                state = State.LOADING
            }
        }
        animator.start()
    }

    var state: State
        get() = mState
        set(state) {
            mState = state
            when (state) {
                State.LOADING -> {
                    onLoadingListener.invoke()
                }
                State.NORMAL -> {
                    lineProgress = 1f
                    circleProgress = 0f
                    textAlpha = 255
                }
                State.COMPLETE -> {
                }
                else -> {}
            }
            invalidate()
        }

    init {
        initAttrs(context, attrs)
        mDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                if (mState == State.NORMAL) startAlpha()
                return super.onSingleTapUp(e)
            }
        })
    }
}