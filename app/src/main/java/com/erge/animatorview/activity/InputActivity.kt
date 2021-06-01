package com.erge.animatorview.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.Toast
import com.erge.animatorview.R
import com.erge.animatorview.form.FormValidator
import com.erge.animatorview.view.InputLayout

class InputActivity : AppCompatActivity() {

    private lateinit var il_name: InputLayout
    private lateinit var il_pwd: InputLayout
    private lateinit var btn_submit: Button

    private var validator = FormValidator()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_input)
        il_name = findViewById(R.id.il_name)
        il_pwd = findViewById(R.id.il_pwd)
        btn_submit = findViewById(R.id.btn_submit)
        btn_submit.setOnClickListener { submit() }

        validator.success = {
            btn_submit.isEnabled = true
            Toast.makeText(this, "all correct", Toast.LENGTH_SHORT).show()
        }

        //
        validator.fail = {
            btn_submit.isEnabled = false
        }

        val focus = View.OnFocusChangeListener { v, hasFocus -> }

        il_name.editText.onFocusChangeListener = focus
    }

    fun submit() {

    }
}