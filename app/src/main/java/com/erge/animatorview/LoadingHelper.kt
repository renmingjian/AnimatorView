package com.erge.animatorview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.widget.Toast
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.motion.widget.TransitionAdapter
import androidx.constraintlayout.widget.ConstraintSet
import com.airbnb.lottie.LottieAnimationView

class LoadingHelper(activity: Activity, private val targetEndView: View) {

    private var root: MotionLayout
    private var targetStartView: View
    private val array = IntArray(2)
    private var contentParent: ViewGroup? = null
    private var lottieView: LottieAnimationView

    init {
        contentParent = activity.window.decorView.findViewById(Window.ID_ANDROID_CONTENT)
        val loadingLayout = LayoutInflater.from(activity)
            .inflate(R.layout.lottie_loading, contentParent, false) as ViewGroup
        contentParent!!.addView(loadingLayout)
        root = contentParent!!.findViewById(R.id.root)
        targetStartView = contentParent!!.findViewById(R.id.view)
        lottieView = contentParent!!.findViewById(R.id.lottieView)

        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                // 给view设置最后一帧时图片的大小
                targetStartView.visibility = View.VISIBLE
                lottieView.visibility = View.INVISIBLE


                targetStartView.performClick()
                Toast.makeText(activity, "onAnimationEnd", Toast.LENGTH_SHORT).show()

            }
        })

        root.addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                lottieView.visibility = View.GONE
                contentParent!!.removeView(loadingLayout)
            }
        })

        // loading过程
        targetStartView.postDelayed({
            start()
        }, 300)
    }

    fun start() {
        val constraintSet = root.getConstraintSet(R.xml.scene1)
        val contentArray = IntArray(2)
        contentParent!!.getLocationInWindow(contentArray)
        targetEndView.getLocationInWindow(array)
        constraintSet.connect(
            R.id.view,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            array[1] - contentArray[1]
        )
        constraintSet.connect(
            R.id.view,
            ConstraintSet.START,
            ConstraintSet.PARENT_ID,
            ConstraintSet.START,
            array[0]
        )
        constraintSet.applyTo(root)
    }

}