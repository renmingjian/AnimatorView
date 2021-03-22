package com.erge.animatorview

import java.util.regex.Pattern

/**
 * Created by erge 3/17/21 7:37 PM
 */
class HKID {

    fun isValidate(value: String): String {
        if (!Pattern.compile("^[A-Z]{1,2}[0-9]{6}[0-9A]$").matcher(value).matches()) {
            return "FORMAT_ERROR"
        }

        var normalized = value.toUpperCase()
        if (Pattern.compile("^[A-Z]{1}\\d+").matcher(value).matches()) {
            normalized = " $normalized"
        }

        val alphabets = "ABCDEFGHIJKLMNOPQRSTUVWXYZ"
        val numbers = "0123456789"
        val codeMap = mutableMapOf<String, Int>()

        numbers.forEach {
            codeMap[it.toString()] = it.toString().toInt()
        }
        alphabets.forEach {
            codeMap[it.toString()] = it.toInt() - 55
        }
        codeMap[" "] = 36

        val codes = mutableListOf<Int>()

        for ((index, value) in normalized.withIndex()) {
            codes.add(index, codeMap[value.toString()]!!)
        }

        val multipliers = arrayOf(9, 8, 7, 6, 5, 4, 3, 2)
        var sum = 0
        for ((index, value) in multipliers.withIndex()) {
            sum += value * codes[index]
        }

        val check = when(val remainder = sum % 11) {
            0 -> "0"
            1 -> "A"
            else -> (11 - remainder).toString()
        }
        val valid = check == value[value.length - 1].toString()
        return if (valid) "VALID" else "CHECKSUM_FAILED"
    }

}