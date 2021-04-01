package com.erge.animatorview.view

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.text.TextUtils
import android.util.Log
import android.view.MotionEvent
import android.view.View
import androidx.core.view.ViewCompat
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.utils.Utils
import kotlin.math.abs

/**
 * Created by erge 3/30/21 3:17 PM
 */
class TagsView(context: Context) : View(context) {

    companion object {
        val DOT_RADIUS = Utils.dp2px(6f)
        val LEFT_PADDING = Utils.dp2px(10f) + DOT_RADIUS
        val MIDDLE_PADDING = Utils.dp2px(10f)
        val RIGHT_PADDING = Utils.dp2px(15f)
        val RECT_HEIGHT = Utils.dp2px(40f)
        const val TOUCH_SLOP = 12
    }

    private lateinit var targetView: View
    private val paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private var downX: Float = 0f
    private var downY: Float = 0f
    var data: MutableList<TagLocation>? = null
        set(value) {
            field = value
            resetLocation()
            invalidate()
        }

    var itemTagClick: (TagLocation) -> Unit = {}

    private fun resetLocation() {
        paint.textSize = Utils.sp2px(12F)
        data?.let {
            for (item in it) {
                // 文字实际所需宽度
                val textWidth = paint.measureText(item.name)
                // 如果整个矩形宽度从xy的位置向右伸展会超出View右侧边距，则矩形向左伸展
                if (item.x + MIDDLE_PADDING + textWidth + RIGHT_PADDING > targetView.width) {
                    item.rectL = item.x - MIDDLE_PADDING - textWidth - RIGHT_PADDING
                    item.rectR = item.x + LEFT_PADDING
                    item.textStart = item.rectL + RIGHT_PADDING
                } else { // 否则矩形向右伸展
                    item.rectL = item.x - LEFT_PADDING
                    item.rectR = item.x + MIDDLE_PADDING + textWidth + RIGHT_PADDING
                    item.textStart = item.x + MIDDLE_PADDING
                }
                item.rectT = item.y - RECT_HEIGHT / 2
                item.rectB = item.y + RECT_HEIGHT / 2
            }
        }
    }

    constructor(targetView: View) : this(targetView.context) {
        this.targetView = targetView
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    var dispatch = true
    override fun dispatchTouchEvent(event: MotionEvent?): Boolean {
        Log.e("tagView", "onTouchEvent")
        when (event?.action) {
            MotionEvent.ACTION_DOWN -> {
                downX = event.x
                downY = event.y
                return true
            }
            MotionEvent.ACTION_UP -> {
                data?.let {
                    val upX = event.x ?: 0f
                    val upY = event.y ?: 0f
                    Log.e("tagView", "upX = $upX, upY = $upY")
                    for (item in it) {
                        if (abs(upX - downX) <= TOUCH_SLOP
                            && abs(upY - downY) <= TOUCH_SLOP
                            && (upX >= item.rectL && upX <= item.rectR && upY >= item.rectT && upY <= item.rectB)
                        ) {
                            itemTagClick.invoke(item)
                            Log.e("tagView", "click")
                            dispatch = true
                        }
                    }
                }
            }
        }
        return dispatch
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        data?.let {
            for (item in it) {
                drawRect(canvas, item)
                drawDot(canvas, item)
                drawText(canvas, item)
            }
        }
        canvas?.restore()
    }

    private fun drawDot(canvas: Canvas?, item: TagLocation) {
        paint.color = Color.parseColor("#ff0000")
        paint.style = Paint.Style.FILL
        canvas?.drawCircle(item.x, item.y, DOT_RADIUS, paint)
    }

    private fun drawRect(canvas: Canvas?, item: TagLocation) {
        paint.color = Color.parseColor("#22000000")
        paint.style = Paint.Style.FILL
        canvas?.drawRoundRect(item.rectL, item.rectT, item.rectR, item.rectB, 50f, 50f, paint)
    }

    private fun drawText(canvas: Canvas?, item: TagLocation) {
        if (TextUtils.isEmpty(item.name)) return
        paint.color = Color.parseColor("#ffffff")
        paint.textAlign = Paint.Align.LEFT
        val fontMetrics = Paint.FontMetrics()
        paint.getFontMetrics(fontMetrics)
        val offset = (fontMetrics.ascent + fontMetrics.descent) / 2
        val baseLine = item.y - offset
        canvas?.drawText(item.name, item.textStart, baseLine, paint)
    }

}