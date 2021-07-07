package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import com.erge.animatorview.R
import com.erge.animatorview.view.PasswordView

class PasswordActivity : AppCompatActivity() {

    private lateinit var pwv: PasswordView
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_password)

        pwv = findViewById(R.id.pwv)

    }

    fun click(view: View) {
        pwv.state = PasswordView.STATE_ERROR
    }
}