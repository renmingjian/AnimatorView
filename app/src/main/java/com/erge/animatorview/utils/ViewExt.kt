package com.erge.animatorview.utils

import android.view.ViewGroup
import android.widget.TextView

/**
 * Created by eryemj 2021/10/21
 */

fun TextView.fitTextSize() {
    scaleX = 1f
    scaleY = 1f
    post {
        val parentView = parent as ViewGroup
        val parentWidth = parentView.width
        println("parentWidth = $parentWidth, width = $width")
        if (width <= parentWidth) return@post
        val scale: Float = parentWidth.toFloat() / width.toFloat()
        scaleX = scale
        scaleY = scale
        // w / s = pw
    }
}