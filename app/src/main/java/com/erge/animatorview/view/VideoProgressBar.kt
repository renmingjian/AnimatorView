package com.erge.animatorview.view

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.AnimatorSet
import android.animation.ObjectAnimator
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import android.view.animation.AccelerateDecelerateInterpolator
import android.view.animation.LinearInterpolator

/**
 * Created by eryemj 2022/1/7
 */
class VideoProgressBar(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private var animatorSet = AnimatorSet()
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var state = 0
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

        paint.strokeWidth = (height / 2).toFloat()
        paint.color = Color.parseColor("#ffffffff")
        paint.alpha = (0.3 * 255).toInt()
        canvas?.drawLine(-(width / 2f), height / 2f, width / 2f, height / 2f, paint)

        if (state == 0) {
            paint.color = Color.WHITE
            paint.alpha = (loadingProgress * 255).toInt()
            canvas?.drawLine(
                -(width / 2f * loadingProgress),
                height / 2f,
                width / 2f * loadingProgress,
                height / 2f,
                paint
            )
        } else {
            paint.alpha = ((1 - gradientProgress) * 255).toInt()
            canvas?.drawLine(-(width / 2f), height / 2f, width / 2f, height / 2f, paint)
        }
        canvas?.restore()
    }

    private fun startLoadingAnim() {
        val loadingAnimator = ObjectAnimator.ofFloat(this, "loadingProgress", 0f, 1f)
        loadingAnimator?.duration = 1000
        loadingAnimator?.interpolator = AccelerateDecelerateInterpolator()
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

        val gradientAnimator = ObjectAnimator.ofFloat(this, "gradientProgress", 0f, 1f)
        gradientAnimator?.duration = 500
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