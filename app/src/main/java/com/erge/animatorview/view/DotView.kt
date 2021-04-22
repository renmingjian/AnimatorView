package com.erge.animatorview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.util.AttributeSet
import android.view.View
import com.erge.animatorview.utils.Utils

/**
 * Created by erge 4/15/21 1:53 PM
 */
class DotView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    val paint = Paint()

    init {
        paint.isAntiAlias = true
        paint.color = Color.parseColor("#b7bacb")
//        paint.strokeWidth = 0f
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.let {
            canvas.drawCircle(width / 2f, height / 2f, Utils.dp2px(3f), paint)
        }

    }

}