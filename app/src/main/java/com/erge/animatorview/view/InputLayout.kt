package com.erge.animatorview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.erge.animatorview.R

/**
 * Created by eryemj 2021/5/31
 */
class InputLayout(context: Context, attributeSet: AttributeSet) :
    ConstraintLayout(context, attributeSet) {

    var editText: EditText
    var ivClear: ImageView
    var tvError: TextView

    init {
        LayoutInflater.from(context).inflate(R.layout.layout_input, this, true)
        editText = findViewById(R.id.editText)
        ivClear = findViewById(R.id.iv_clear)
        tvError = findViewById(R.id.tv_error)
        ivClear.setOnClickListener {
            editText.setText("")
            tvError.visibility = View.GONE
        }
    }

}