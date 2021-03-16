package com.erge.animatorview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.FrameLayout
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import com.airbnb.lottie.LottieAnimationView

class PayLoadingHelper(activity: Activity, private val targetEndView: View) {
    private var motionLayout: MotionLayout
    private var targetStartView: View
    private val targetEndViewLocation = IntArray(2)
    private var contentParent: ViewGroup? = null
    private var lottieView: LottieAnimationView
    private var lottieContainer: FrameLayout

    init {
        contentParent = activity.window.decorView.findViewById(Window.ID_ANDROID_CONTENT)
        val loadingLayout = LayoutInflater.from(activity)
            .inflate(R.layout.pay_lottie_loading, contentParent, false) as ViewGroup
        contentParent!!.addView(loadingLayout)
        motionLayout = contentParent!!.findViewById(R.id.root)
        targetStartView = contentParent!!.findViewById(R.id.iv_holder)
        lottieContainer = contentParent!!.findViewById(R.id.fl_lottie_container)
        lottieView = contentParent!!.findViewById(R.id.lottieView)

        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                lottieContainer.alpha = 0f
                lottieContainer.visibility = View.GONE
                targetStartView.visibility = View.VISIBLE
                targetStartView.performClick()
            }
        })

        motionLayout.addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
//                contentParent!!.removeView(loadingLayout)
                loadingLayout.alpha = 0.3f
            }
        })

        targetStartView.postDelayed({
//            reConstraint()
        }, 400)
    }

    private fun reConstraint() {
        val constraintSet = motionLayout.getConstraintSet(R.xml.pay_scene)
        val contentArray = IntArray(2)
        contentParent!!.getLocationInWindow(contentArray)
        targetEndView.getLocationInWindow(targetEndViewLocation)
        constraintSet.connect(
            R.id.iv_holder,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            targetEndViewLocation[1] - contentArray[1]
        )
        constraintSet.connect(
            R.id.iv_holder,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            targetEndViewLocation[0]
        )
        constraintSet.applyTo(motionLayout)
    }

}