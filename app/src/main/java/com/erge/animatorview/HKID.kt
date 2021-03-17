package com.erge.animatorview

import java.util.regex.Pattern

/**
 * Created by erge 3/17/21 7:37 PM
 */
class HKID {

    fun isValidate(value: String): Boolean {
        if (!Pattern.compile("^[A-Z]{1,2}[0-9]{6}[0-9A]$").matcher(value).matches()) {
            return false
        }

        var normalized = value.toUpperCase()
        if (Pattern.compile("^[A-Z]{1}\\d+").matcher(value).matches()) {
            normalized = " $normalized"
        }

        val alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val codeMap = mutableMapOf<String, Int>()

        numbers.forEach {
            codeMap[it.toString()] = it.toInt()
        }
        alphabets.forEach {
            codeMap[it.toString()] = it.toInt() - 55
        }
        codeMap[" "] = 36

        return false
    }

}