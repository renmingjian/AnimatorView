package com.erge.animatorview.form

import android.widget.TextView

/**
 * Created by eryemj 2021/5/31
 */
data class ValidateRuleInfo(
    val validateRule: ValidateRule,
    val view: TextView,
    val errorMsg: String? = null,
    var isValidate: Boolean = true
)