package com.erge.animatorview.view

import android.content.Context
import android.content.res.TypedArray
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.erge.animatorview.R

/**
 * Created by erge 4/2/21 11:33 AM
 */
class TriangleView(context: Context, attributeSet: AttributeSet): View(context, attributeSet) {

    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val path = Path()

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TriangleView)
        val color = ta.getColor(R.styleable.TriangleView_triangle_fill_color, Color.parseColor("#000000"));
        paint.color = color
        paint.style = Paint.Style.FILL
        ta.recycle()
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        path.moveTo(width / 2f, 0f)
        path.lineTo(0f, height.toFloat())
        path.lineTo(width.toFloat(), height.toFloat())
        path.close()
        canvas?.drawPath(path, paint)
        canvas?.restore()
    }

}