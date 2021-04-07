package com.erge.animatorview.view

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.utils.Utils

/**
 * Created by erge 4/7/21 10:52 AM
 */
class TagLayout(context: Context) : FrameLayout(context) {

    private val SWOP_DOWN = 0
    private val SWOP_UP = 1
    private val DEVIATION_LEFT = 10
    private val DEVIATION_MIDDLE = 11
    private val DEVIATION_RIGHT = 12
    private val DOT_TRIANBLE_MARGIN = Utils.dp2px(4f)

    private var vDot: View
    private var tvTagName: TextView
    private var ivTriangle: TriangleView

    init {
        val itemView: View = LayoutInflater.from(context).inflate(R.layout.item_tag4, this, true)
        vDot = itemView.findViewById(R.id.v_dot)
        tvTagName = itemView.findViewById(R.id.tv_tag_name)
        ivTriangle = itemView.findViewById(R.id.iv_triangle)
    }

    fun setTag(data: TagLocation) {
//        visibility = if (data.animated) {
//            View.VISIBLE
//        } else {
//            View.INVISIBLE
//        }
        val viewGroup: ViewGroup = parent as ViewGroup

        post {
            calculateLocation(data, viewGroup)
            resetLocation(data)
        }
    }

    fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup) {
        val parentWidth = tagLocation.parentWidth
        val parentHeight = tagLocation.parentHeight
        val itemWidth = width

        // 横向位置确定
        val leftBoundary = tagLocation.x - itemWidth / 2 - tagLocation.leftMargin
        val rightBoundary = tagLocation.x + itemWidth / 2 + tagLocation.rightMargin
        if (rightBoundary <= parentWidth && leftBoundary >= 0) {  // 布局居中
            tagLocation.rectL = tagLocation.x - itemWidth / 2
            tagLocation.rectR = tagLocation.rectL + itemWidth
            tagLocation.typeH = DEVIATION_MIDDLE
        } else if (rightBoundary > parentHeight) { // 布局偏右
            tagLocation.rectR = parentWidth - tagLocation.rightMargin
            tagLocation.rectL = tagLocation.rectR - itemWidth
            tagLocation.typeH = DEVIATION_RIGHT
        } else if (leftBoundary < 0) { // 布局偏左
            tagLocation.rectL = tagLocation.leftMargin
            tagLocation.rectR = tagLocation.rectL + itemWidth
            tagLocation.typeH = DEVIATION_LEFT
        }

        // 竖向位置确定
        when {
            tagLocation.y - vDot.height / 2 - ivTriangle.height - tvTagName.height - tagLocation.topMargin - DOT_TRIANBLE_MARGIN >= 0 -> {
                tagLocation.rectT =
                    tagLocation.y - vDot.height / 2 - ivTriangle.height - tvTagName.height - DOT_TRIANBLE_MARGIN
                tagLocation.typeV = SWOP_DOWN
                ivTriangle.rotationX = 180f
            }
            else -> {
                tagLocation.rectT = tagLocation.y - vDot.height / 2
                tagLocation.typeV = SWOP_UP
            }
        }
        resetInnerViewsLocation(tagLocation, parentView)
    }

    /**
     * 计算Tag内部各个View的位置
     */
    private fun resetInnerViewsLocation(
        tagLocation: TagLocation,
        parentView: ViewGroup
    ) {
        val dotLayoutParams = vDot.layoutParams as FrameLayout.LayoutParams
        val textLayoutParams = tvTagName.layoutParams as FrameLayout.LayoutParams
        val triangleLayoutParams = ivTriangle.layoutParams as FrameLayout.LayoutParams
        // 圆点dotView在水平方向的位置跟typeH无关，三种类型的计算方式是一样的
        dotLayoutParams.leftMargin =
            (tagLocation.x - tagLocation.rectL - vDot.width / 2).toInt()
        // 三角形的水平位置也与类型无关，三种类型的计算方式一样
        triangleLayoutParams.leftMargin =
            (tagLocation.x - tagLocation.rectL - ivTriangle.width / 2).toInt()
        when (tagLocation.typeV) {
            SWOP_DOWN -> {
                dotLayoutParams.topMargin =
                    (tagLocation.y - tagLocation.rectT - vDot.height / 2).toInt()
                triangleLayoutParams.topMargin = tvTagName.height
                triangleLayoutParams.bottomMargin = DOT_TRIANBLE_MARGIN.toInt()
                textLayoutParams.topMargin = 0
            }
            else -> {
                dotLayoutParams.topMargin = 0
                triangleLayoutParams.topMargin = vDot.height + DOT_TRIANBLE_MARGIN.toInt()
                textLayoutParams.topMargin =
                    vDot.height + ivTriangle.height + DOT_TRIANBLE_MARGIN.toInt()
            }
        }
        parentView.requestLayout()
    }

    private fun resetLocation(item: TagLocation) {
        val newLayoutParams =
            layoutParams as LayoutParams
        newLayoutParams.leftMargin = item.rectL.toInt()
        newLayoutParams.topMargin = item.rectT.toInt()
        layoutParams.width = (item.rectR - item.rectL).toInt()
        this.layoutParams = newLayoutParams
    }
}