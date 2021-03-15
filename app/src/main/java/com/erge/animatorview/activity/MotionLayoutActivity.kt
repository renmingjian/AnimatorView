package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import android.view.Window
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.erge.animatorview.R
import com.erge.animatorview.utils.Utils

class MotionLayoutActivity : AppCompatActivity() {

    private lateinit var root: MotionLayout
    private lateinit var v_test: View
    val array = IntArray(2)
    private lateinit var constraintSet1: ConstraintSet

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        val view: View = findViewById(R.id.view)
        v_test = findViewById(R.id.v_test)


        root = findViewById(R.id.root)
        constraintSet1 = root.getConstraintSet(R.xml.scene1)

        view.postDelayed({
            view.performClick()
        }, 1000)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        val contentParent = window.decorView.findViewById<ViewGroup>(Window.ID_ANDROID_CONTENT)
        val contentArray = IntArray(2)
        contentParent.getLocationInWindow(contentArray)
        println("barHeight = ${contentArray[1]}")
        v_test.getLocationInWindow(array)
        constraintSet1.connect(R.id.view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, array[1] - contentArray[1])
        constraintSet1.connect(R.id.view, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, array[0])
        println("xxx = ${array[0]}")
    }
}