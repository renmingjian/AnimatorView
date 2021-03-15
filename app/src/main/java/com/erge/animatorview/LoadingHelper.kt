package com.erge.animatorview

import android.app.Activity
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintSet

class LoadingHelper(private val activity: Activity, private val view: View) {

    private lateinit var root: MotionLayout
    private lateinit var v_test: View
    val array = IntArray(2)
    private lateinit var constraintSet: ConstraintSet
    private var contentParent: ViewGroup? = null

    init {
        contentParent = activity.window.decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val loadingLayout = LayoutInflater.from(activity)
            .inflate(R.layout.lottie_loading, contentParent, true) as ViewGroup
        root = loadingLayout.findViewById(R.id.root)
        v_test = loadingLayout.findViewById(R.id.v_test)
        constraintSet = root.getConstraintSet(R.xml.scene1)

        // loading过程
        view.postDelayed({
            start()
        }, 300)
    }

    fun start() {
        val contentArray = IntArray(2)
        contentParent!!.getLocationInWindow(contentArray)
        println("barHeight = ${contentArray[1]}")
        v_test.getLocationInWindow(array)
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
        println("xxx = ${array[0]}")

        view.performClick()
    }

}