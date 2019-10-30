package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.erge.animatorview.R
import com.erge.animatorview.view.ChargeView

class ChargeActivity : AppCompatActivity() {

    private lateinit var chargeView: ChargeView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_charge)

        chargeView = findViewById(R.id.chargeView);
    }

    fun reset(view: View) {
        chargeView.startAnim()
    }
}
