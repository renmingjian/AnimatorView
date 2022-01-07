package com.erge.animatorview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator

/**
 * Created by eryemj 2022/1/7
 */
class VideoLoadingBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var animatorSet = AnimatorSet()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var state = 0
    private val rectFBg: RectF = RectF()
    private val rectF: RectF = RectF()
    private var gradientProgress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }
    private var loadingProgress: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        startLoadingAnim()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        canvas?.translate((width / 2).toFloat(), (height / 2).toFloat())

        val radius = height / 2f
        paint.strokeWidth = height.toFloat()
        paint.color = Color.parseColor("#ffffffff")
        paint.alpha = (0.3 * 255).toInt()
        rectFBg.left = -(width / 2f)
        rectFBg.top = -(height / 2f)
        rectFBg.right = width / 2f
        rectFBg.bottom = height / 2f
        canvas?.drawRoundRect(rectFBg, radius, radius, paint)

        if (state == 0) {
            paint.color = Color.WHITE
            paint.alpha = (loadingProgress * 255 * 0.6).toInt()
            rectF.left = -(width / 2f * loadingProgress)
            rectF.top = -(height / 2f)
            rectF.right = width / 2f * loadingProgress
            rectF.bottom = height / 2f
            canvas?.drawRoundRect(rectF, radius, radius, paint)
        } else {
            paint.alpha = ((1 - gradientProgress) * 255).toInt()
            rectF.left = -(width / 2f)
            rectF.top = -(height / 2f)
            rectF.right = width / 2f
            rectF.bottom = height / 2f
            canvas?.drawRoundRect(rectF, radius, radius, paint)
        }
        canvas?.restore()
    }

    private fun startLoadingAnim() {
        val loadingAnimator = ObjectAnimator.ofFloat(this, "loadingProgress", 0f, 1f)
        loadingAnimator?.duration = 340
        loadingAnimator?.interpolator = LinearInterpolator()
        loadingAnimator?.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                state = 1
            }

            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                state = 0
            }
        })

        val gradientAnimator = ObjectAnimator.ofFloat(this, "gradientProgress", 0.4f, 1f)
        gradientAnimator?.duration = 340
        gradientAnimator?.interpolator = LinearInterpolator()

        animatorSet.playSequentially(loadingAnimator, gradientAnimator)
        animatorSet.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                animatorSet.start()
            }
        })
        animatorSet.start()
    }

    override fun onVisibilityChanged(changedView: View, visibility: Int) {
        super.onVisibilityChanged(changedView, visibility)
        if (visibility == GONE) {
            animatorSet.pause()
        } else {
            animatorSet.start()
        }
    }

}