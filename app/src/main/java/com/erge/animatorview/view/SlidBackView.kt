package com.erge.animatorview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.Path
import android.util.AttributeSet
import android.view.View
import com.erge.animatorview.utils.Utils

/**
 * Created by erge 2021/5/6 6:35 下午
 */
class SlidBackView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val bgPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val textPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val bgPath = Path()
    private val fontMetrics = Paint.FontMetrics()
    var currentWidth = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        bgPaint.color = Color.parseColor("#000000")
        bgPaint.style = Paint.Style.FILL
        textPaint.color = Color.parseColor("#ffffff")
        textPaint.style = Paint.Style.FILL
        textPaint.textSize = Utils.sp2px(14f)
        textPaint.textAlign = Paint.Align.CENTER
    }

    override fun onDraw(canvas: Canvas) {
        canvas.save()
        drawBg(canvas)
        drawText(canvas)
        canvas.restore()
    }

    private fun drawBg(canvas: Canvas) {
        bgPath.reset()
        bgPath.moveTo(currentWidth, 0f)
        bgPath.cubicTo(
            currentWidth,
            measuredHeight * 2f / 9f,
            0f,
            measuredHeight.toFloat() / 3f,
            0f,
            measuredHeight.toFloat() / 2f
        )
        bgPath.cubicTo(
            0f,
            measuredHeight * 2f / 3f,
            currentWidth,
            measuredHeight * 7f / 9f,
            currentWidth,
            measuredHeight.toFloat()
        )
        canvas.drawPath(bgPath, bgPaint)
    }

    private fun drawText(canvas: Canvas) {
        textPaint.getFontMetrics(fontMetrics)
        val baseLine = measuredHeight / 2f - (fontMetrics.ascent + fontMetrics.descent) / 2
        canvas.drawText("More", measuredWidth / 2f, baseLine, textPaint)
    }
}