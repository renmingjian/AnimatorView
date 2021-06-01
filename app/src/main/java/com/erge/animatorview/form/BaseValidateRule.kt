package com.erge.animatorview.form

import android.widget.TextView

/**
 * Created by eryemj 2021/5/31
 */
abstract class BaseValidateRule(var view: TextView, var errorMsg: String? = ""): ValidateRule