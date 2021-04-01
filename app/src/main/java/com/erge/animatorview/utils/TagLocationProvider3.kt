package com.erge.animatorview.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.erge.animatorview.R
import com.erge.animatorview.bean.LEFT_TO_RIGHT
import com.erge.animatorview.bean.RIGHT_TO_LEFT
import com.erge.animatorview.bean.TagLocation

/**
 * Created by erge 4/1/21 1:41 PM
 */
class TagLocationProvider3(override var itemClick: (TagLocation) -> Unit) : TagLocationProvider {

    private val SWOP_DOWN = 0
    private val SWOP_UP = 1
    private val DEVIATION_LEFT = 10
    private val DEVIATION_MIDDLE = 11
    private val DEVIATION_RIGHT = 12
    private lateinit var tagLocation: TagLocation
    private lateinit var parentView: ViewGroup
    private lateinit var tagView: FrameLayout
    private lateinit var tvTagName: TextView
    private lateinit var vDot: View
    private lateinit var ivTriangle: ImageView

    override fun addView(tagLocation: TagLocation, parent: ViewGroup) {
        this.tagLocation = tagLocation
        this.parentView = parent
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag4, parent, false) as FrameLayout
        tagView = itemView
        itemView.setOnClickListener { itemClick.invoke(tagLocation) }
        tvTagName = itemView.findViewById(R.id.tv_tag_name)
        vDot = itemView.findViewById(R.id.v_dot)
        ivTriangle = itemView.findViewById(R.id.iv_triangle)
        tvTagName.text = tagLocation.name
        parent.addView(itemView)
        parent.postDelayed({
            calculateLocation(tagLocation, parent, itemView)
            resetLocation(tagLocation, itemView)
        }, 34)
    }

    override fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup, tagView: View) {
        val parentWidth = parentView.width
        val parentHeight = parentView.height
        val itemWidth = tagView.width

        // 横向位置确定
        val leftBoundary = tagLocation.x - itemWidth / 2 - tagLocation.leftMargin
        val rightBoundary = tagLocation.x + itemWidth / 2 + tagLocation.rightMargin
        println("leftBoundary = $leftBoundary, rightBoundary = $rightBoundary, parentWidth = $parentWidth, itemWidth = ${tagView.width}, name = ${tagLocation.name}")
        if (rightBoundary <= parentWidth && leftBoundary >= 0) {  // 布局居中
            tagLocation.rectL = tagLocation.x - itemWidth / 2
            tagLocation.typeH = DEVIATION_MIDDLE
        } else if (rightBoundary > parentHeight) { // 布局偏右
            tagLocation.rectR = parentWidth - tagLocation.rightMargin
            tagLocation.rectL = tagLocation.rectR - itemWidth
            tagLocation.typeH = DEVIATION_RIGHT
        } else if (leftBoundary < 0) { // 布局偏左
            tagLocation.rectL = tagLocation.leftMargin
            tagLocation.typeH = DEVIATION_LEFT
        }

        // 竖向位置确定
        when {
            tagLocation.y - vDot.height / 2 - ivTriangle.height - tvTagName.height - tagLocation.topMargin >= 0 -> {
                tagLocation.rectT =
                    tagLocation.y - vDot.height / 2 - ivTriangle.height - tvTagName.height
                tagLocation.typeV = SWOP_DOWN
            }
            else -> {
                tagLocation.rectT = tagLocation.y - vDot.height / 2
                tagLocation.typeV = SWOP_UP
            }
        }
        resetInnerViewsLocation(tagLocation, parentView, tagView)
    }

    private fun resetInnerViewsLocation(
        tagLocation: TagLocation,
        parentView: ViewGroup,
        tagView: View
    ) {
        val dotLayoutParams = vDot.layoutParams
        when(tagLocation.typeH) {
            DEVIATION_LEFT -> {

            }
        }
    }

    private fun resetLocation(item: TagLocation, itemView: View) {
        val layoutParams: FrameLayout.LayoutParams =
            itemView.layoutParams as FrameLayout.LayoutParams
        layoutParams.leftMargin = item.rectL.toInt()
        layoutParams.topMargin = item.rectT.toInt()
        itemView.layoutParams = layoutParams
    }

}