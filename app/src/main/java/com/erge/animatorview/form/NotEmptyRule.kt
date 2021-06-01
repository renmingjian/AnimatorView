package com.erge.animatorview.form

/**
 * Created by eryemj 2021/5/31
 */
class NotEmptyRule : ValidateRule {

    override fun isValidate(value: String?): Boolean {
        return value.isNullOrEmpty()
    }

}