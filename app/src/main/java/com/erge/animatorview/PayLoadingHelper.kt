package com.erge.animatorview

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.app.Activity
import android.app.Service
import android.os.Vibrator
import android.view.*
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

    init {
        // 当页面加载时，使用pay_lottie_loading作为contentParent的根View，因为contentParent是一个FrameLayout，
        // 所以addView后pay_lottie_loading会覆盖原Activity布局，从而展示动画。
        contentParent = activity.window.decorView.findViewById(Window.ID_ANDROID_CONTENT)
        val loadingLayout = LayoutInflater.from(activity)
            .inflate(R.layout.pay_lottie_loading, contentParent, false) as ViewGroup
        contentParent!!.addView(loadingLayout)
        motionLayout = contentParent!!.findViewById(R.id.root)
        targetStartView = contentParent!!.findViewById(R.id.iv_holder)
        lottieView = contentParent!!.findViewById(R.id.lottieView)

        // lottie动画结束后，让lottieView不可见，让占位的ImageView展示并且开启MotionLayout动画
        lottieView.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                lottieView.alpha = 0f
                lottieView.visibility = View.GONE
                targetStartView.visibility = View.VISIBLE
                motionLayout.transitionToEnd()
            }
        })

        // MotionLayout动画结束后，移除动画布局，展示原Activity的布局
        motionLayout.addTransitionListener(object : TransitionAdapter() {
            override fun onTransitionCompleted(motionLayout: MotionLayout?, currentId: Int) {
                super.onTransitionCompleted(motionLayout, currentId)
                targetEndView.visibility = View.VISIBLE
                contentParent!!.removeView(loadingLayout)
            }
        })

        targetEndView.visibility = View.INVISIBLE
        targetStartView.postDelayed({
            reConstraint()
        }, 400)

        val vibrate: Vibrator = activity.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vibrate.vibrate(50)
    }

    /**
     * 对不可见的约束占位布局做重新约束。
     * 做动画的图片占位View-iv_holder需要进行缩放，但是缩放后位置不对，所以添加了不可见的constraintView，让iv_holder
     * 以他作为约束布局，这样动画结束后iv_holder的位置可以很好的与constraintView重合。而constraintView的位置是动态
     * 确定的，也就是外界传递过来的targetEndView在什么位置，constraintView就在什么位置，而这个位置的确定是从
     * targetEndView在页面的位置确定的，有了targetEndView的位置，就可以让constraintView以marginStart和marginTop
     * 来确定
     */
    private fun reConstraint() {
        val contentArray = IntArray(2)
        contentParent!!.getLocationInWindow(contentArray)
        targetEndView.getLocationInWindow(targetEndViewLocation)
        val constraintSet = motionLayout.getConstraintSet(R.xml.pay_scene)
        with(constraintSet) {
            connect(
                R.id.constraintView,
                ConstraintSet.TOP,
                ConstraintSet.PARENT_ID,
                ConstraintSet.TOP,
                targetEndViewLocation[1] - contentArray[1]
            )
            connect(
                R.id.constraintView,
                ConstraintSet.START,
                ConstraintSet.PARENT_ID,
                ConstraintSet.START,
                targetEndViewLocation[0]
            )
            applyTo(motionLayout)
        }
    }

}