package com.erge.animatorview.view

import android.animation.Keyframe
import android.animation.ObjectAnimator
import android.animation.PropertyValuesHolder
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
class LoadingButton2(context: Context, attrs: AttributeSet?) : View(context, attrs) {
    private var mState = State.NORMAL
    private var mWidth = 0
    private var mHeight = 0
    private var mRadius = 0f

    private val mPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDetector: GestureDetector
    private var mText: String? = null
    private var mOnLoadingListener: OnLoadingListener? = null
    private var mOnClickListener: OnClickListener? = null
    private var mAnimator: ObjectAnimator? = null
    private var mNormalToLoading = false
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
    private var progressStart = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var progressEnd = 0f
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
        // 正常的Button状态
        NORMAL,  // Loading状态
        LOADING,  // Loading完成状态
        COMPLETE,  // Loaing错误状态
        ERROR
    }

    private fun initAttrs(context: Context, attrs: AttributeSet?) {
        val typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton)
        mText = typedArray.getString(R.styleable.LoadingButton_text)
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
        mRadius = (mHeight shr 1).toFloat()
    }

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)
        when (mState) {
            State.NORMAL -> drawButton(canvas)
            State.LOADING -> drawLoading(canvas)
            State.ERROR -> drawError(canvas)
        }
    }

    private fun drawButton(canvas: Canvas) {
        canvas.save()
        mPaint.color = Color.parseColor("#ffd700")
        mPaint.style = Paint.Style.FILL
        val width = mWidth - 2 * mRadius
        if (mNormalToLoading) {
            // 左侧半圆
            canvas.drawArc(
                width * (1f - lineProgress) / 2, 0f, width * (1f - lineProgress) / 2 + mRadius * 2,
                mHeight.toFloat(), 90f, 180f, false, mPaint
            )
            // 中间矩形
            canvas.drawRect(
                width * (1f - lineProgress) / 2 + mRadius, 0f,
                width * lineProgress / 2f + mWidth / 2f, mHeight.toFloat(), mPaint
            )
            // 右侧半圆
            canvas.drawArc(
                mWidth / 2f + width * lineProgress / 2f - mRadius, 0f,
                mWidth / 2f + width * lineProgress / 2f + mRadius,
                mHeight.toFloat(), 270f, 180f, false, mPaint
            )
        } else {
            canvas.drawRoundRect(0f, 0f, mWidth.toFloat(), mHeight.toFloat(), 20f, 20f, mPaint)
            drawText(canvas)
        }
        canvas.restore()
    }

    private fun drawText(canvas: Canvas) {
        // 画文字
        if (!TextUtils.isEmpty(mText)) {
            mPaint.textSize = mHeight / 2f * lineProgress
            mPaint.color = Color.parseColor("#ffffff")
            mPaint.textAlign = Paint.Align.CENTER
            val metrics = Paint.FontMetrics()
            mPaint.getFontMetrics(metrics)
            mPaint.alpha = textAlpha
            val offset = (metrics.ascent + metrics.descent) / 2
            val baseLine = (mHeight shr 1) - offset
            canvas.drawText(mText!!, (mWidth shr 1).toFloat(), baseLine, mPaint)
        } else {
            startGradientAnim()
        }
    }

    private fun drawLoading(canvas: Canvas) {
        canvas.save()
        mPaint.color = Color.parseColor("#ffd700")
        mPaint.strokeWidth = 0f
        mPaint.style = Paint.Style.FILL
        canvas.drawCircle(mWidth / 2f, mHeight / 2f, mRadius, mPaint)
        mPaint.color = Color.parseColor("#000000")
        mPaint.strokeWidth = 6f
        mPaint.style = Paint.Style.STROKE
        canvas.drawArc(
            mWidth / 2 - mRadius + mRadius / 2, mHeight / 2 - mRadius + mRadius / 2,
            mWidth / 2 + mRadius - mRadius / 2, mHeight / 2 + mRadius - mRadius / 2,
            progressStart * 360, (progressEnd - progressStart) * 360,
            false, mPaint
        )
        println("left = " + (mWidth / 2 - mRadius + 10) + "--right = " + (mWidth / 2 + mRadius - 10))
        canvas.restore()
    }

    private fun drawError(canvas: Canvas) {
        canvas.save()
        mPaint.strokeWidth = PAINT_STROKE_WIDTH.toFloat()
        mPaint.style = Paint.Style.STROKE
        canvas.drawCircle(
            (mWidth shr 1).toFloat(),
            (mHeight shr 1).toFloat(),
            mRadius - (PAINT_STROKE_WIDTH shr 1),
            mPaint
        )
        canvas.drawLine(
            (mWidth - mRadius) / 2, (mHeight - mRadius) / 2, (mWidth + mRadius) / 2,
            (mHeight + mRadius) / 2, mPaint
        )
        canvas.drawLine(
            (mWidth - mRadius) / 2, (mHeight + mRadius) / 2, (mWidth + mRadius) / 2,
            (mHeight - mRadius) / 2, mPaint
        )
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
        animator.start()
    }

    /**
     * 开启渐变为圆的效果
     */
    private fun startGradientAnim() {
        mNormalToLoading = true
        val animator = ObjectAnimator.ofFloat(this, "lineProgress", 1f, 0f)
        animator.duration = 400
        animator.addUpdateListener { animation ->
            val value = animation.animatedValue as Float
            if (value == 0f) {
                postDelayed({ state = State.LOADING }, 300)
            }
        }
        animator.start()
    }

    /**
     * loading过程动画
     */
    private fun startLoadingAnim() {
        val keyframeStart1 = Keyframe.ofFloat(0.1f, 0.1f)
        val keyframeStart2 = Keyframe.ofFloat(0.2f, 0.2f)
        val keyframeStart3 = Keyframe.ofFloat(0.8f, 0.9f)
        val keyframeStart4 = Keyframe.ofFloat(1f, 1f)
        val holder1 = PropertyValuesHolder.ofKeyframe(
            "progressStart", keyframeStart1,
            keyframeStart2, keyframeStart3, keyframeStart4
        )
        val keyframeEnd1 = Keyframe.ofFloat(0f, 0f)
        val keyframeEnd2 = Keyframe.ofFloat(0.2f, 0.6f)
        val keyframeEnd3 = Keyframe.ofFloat(0.6f, 0.8f)
        val keyframeEnd4 = Keyframe.ofFloat(0.8f, 1f)
        val holder2 = PropertyValuesHolder.ofKeyframe(
            "progressEnd", keyframeEnd1,
            keyframeEnd2, keyframeEnd3, keyframeEnd4
        )
        mAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holder1, holder2)
        mAnimator!!.duration = 1200
        mAnimator!!.repeatCount = -1
        mAnimator!!.start()
    }

    var state: State
        get() = mState
        set(state) {
            mState = state
            if (state == State.LOADING) {
                startLoadingAnim()
            } else if (state == State.NORMAL) {
                lineProgress = 1f
                circleProgress = 0f
            } else if (state == State.COMPLETE) {
            }
            invalidate()
        }

    fun setOnLoadingListener(onLoadingListener: OnLoadingListener?) {
        mOnLoadingListener = onLoadingListener
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)

        if (visibility == VISIBLE) {
            mAnimator?.start()
        } else {
            mAnimator?.pause()
        }
    }

    override fun onDetachedFromWindow() {
        super.onDetachedFromWindow()
        mAnimator?.pause()
        mAnimator = null
    }

    fun setOnClickListener(onClickListener: OnClickListener?) {
        mOnClickListener = onClickListener
    }

    /**
     * Loading监听
     */
    interface OnLoadingListener {
        fun onStartLoading()
    }

    /**
     * 点击事件监听
     */
    interface OnClickListener {
        // 正常状态下的点击事件
        fun onNormalClick()

        // Loading状态下的点击事件
        fun onLoadingClick()

        // 加载完成后的点击事件
        fun onCompleteClick()

        // 加载错误情况下的点击事件
        fun onErrorClick()
    }

    companion object {
        private const val PAINT_STROKE_WIDTH = 4
    }

    init {
        initAttrs(context, attrs)
        mDetector = GestureDetector(context, object : SimpleOnGestureListener() {
            override fun onDown(e: MotionEvent): Boolean {
                return true
            }

            override fun onSingleTapUp(e: MotionEvent): Boolean {
                when (mState) {
                    State.NORMAL -> {
                        if (mOnClickListener != null) {
                            mOnClickListener!!.onNormalClick()
                        }
                        //                        startGradientAnim();
                        startAlpha()
                    }
                    State.LOADING -> {
                        if (mOnLoadingListener != null) {
                            mOnLoadingListener!!.onStartLoading()
                        }
                        if (mOnClickListener != null) {
                            mOnClickListener!!.onLoadingClick()
                        }
                    }
                    State.COMPLETE -> if (mOnClickListener != null) {
                        mOnClickListener!!.onCompleteClick()
                    }
                    State.ERROR -> if (mOnClickListener != null) {
                        mOnClickListener!!.onErrorClick()
                    }
                }
                return super.onSingleTapUp(e)
            }
        })
    }
}