package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.view.ViewGroup
import androidx.constraintlayout.motion.widget.MotionLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.erge.animatorview.R
import com.erge.animatorview.utils.Utils

class MotionLayoutActivity : AppCompatActivity() {

    private lateinit var root: MotionLayout
    private lateinit var v_test: View
    val array = IntArray(2)

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        val view: View = findViewById(R.id.view)
        v_test = findViewById(R.id.v_test)


        root = findViewById(R.id.root)
        val constraintSet1 = root.getConstraintSet(R.xml.scene1)
        println("constraintSet1 = $constraintSet1")

//         set.connect(mViewSwitcher.getId(), ConstraintSet.TOP, mTitleView.getId(), ConstraintSet.BOTTOM, 50);


//        constraintSet.applyTo(root)

        view.postDelayed({
            v_test.getLocationInWindow(array)
            constraintSet1.connect(R.id.view, ConstraintSet.TOP, ConstraintSet.PARENT_ID, ConstraintSet.TOP, array[1])
            constraintSet1.connect(R.id.view, ConstraintSet.START, ConstraintSet.PARENT_ID, ConstraintSet.START, array[0])
            view.performClick()
            println("xxx = ${array[0]}")
        }, 1000)
    }

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)

    }
}