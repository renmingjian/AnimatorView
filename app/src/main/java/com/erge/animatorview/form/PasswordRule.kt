package com.erge.animatorview.form

import java.util.regex.Pattern

/**
 * Created by eryemj 2021/5/31
 */
class PasswordRule: ValidateRule {

    override fun isValidate(value: String?): Boolean {
        val compile = Pattern.compile("^[1-3]{3}$")
        val matcher = compile.matcher(value)
        return matcher.matches()
    }

}