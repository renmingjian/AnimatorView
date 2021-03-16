package com.erge.animatorview.activity

import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import com.erge.animatorview.PayLoadingHelper
import com.erge.animatorview.R

class MotionLayoutActivity : AppCompatActivity() {

    private lateinit var v_test: View
    private lateinit var loadingHelper: PayLoadingHelper

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_motion_layout)

        v_test = findViewById(R.id.v_test)

        loadingHelper = PayLoadingHelper(this, v_test)
    }

}