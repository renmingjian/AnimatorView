package com.erge.animatorview.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ValueAnimator
import android.graphics.Paint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.view.TriangleView

/**
 * Created by erge 4/1/21 1:41 PM
 */
class TagLocationProvider3(override var itemClick: (TagLocation) -> Unit) : TagLocationProvider {

    private val SWOP_DOWN = 0
    private val SWOP_UP = 1
    private val DEVIATION_LEFT = 10
    private val DEVIATION_MIDDLE = 11
    private val DEVIATION_RIGHT = 12

    private val DOT_HEIGHT = Utils.dp2px(10f)
    private val TRIANGLE_HEIGHT = Utils.dp2px(5f)
    private val TRIANGLE_WIDTH = Utils.dp2px(10f)
    private val TEXT_HEIGHT = Utils.dp2px(28f)

    private val DOT_TRIANBLE_MARGIN = Utils.dp2px(4f)
    private lateinit var parentView: ViewGroup


    override fun addView(tagLocation: TagLocation, parent: ViewGroup) {
        this.parentView = parent
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag4, parent, false) as FrameLayout
        itemView.setOnClickListener { itemClick.invoke(tagLocation) }
        val tvTagName: TextView = itemView.findViewById(R.id.tv_tag_name)
        val vDot: View = itemView.findViewById(R.id.v_dot)
        val ivTriangle: TriangleView = itemView.findViewById(R.id.iv_triangle)
        tvTagName.text = tagLocation.name
        parent.addView(itemView)

        if (tagLocation.animated) {
            tvTagName.visibility = View.VISIBLE
            vDot.visibility = View.VISIBLE
            ivTriangle.visibility = View.VISIBLE
        } else {
            tvTagName.visibility = View.INVISIBLE
            vDot.visibility = View.INVISIBLE
            ivTriangle.visibility = View.INVISIBLE
        }

        calculateLocation(tagLocation, parent, itemView)
        resetLocation(tagLocation, itemView)
    }

    override fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup, tagView: View) {
        val parentWidth = tagLocation.parentWidth
        val parentHeight = tagLocation.parentHeight
        val paint = Paint()
        paint.textSize = Utils.sp2px(12f)
        var itemWidth = paint.measureText(tagLocation.name) + Utils.dp2px(21f)
        if (itemWidth > parentWidth - Utils.dp2px(20f)) {
            itemWidth = parentWidth - Utils.dp2px(20f)
        }

        val tvTagName: TextView = tagView.findViewById(R.id.tv_tag_name)
        val vDot: View = tagView.findViewById(R.id.v_dot)
        val ivTriangle: View = tagView.findViewById(R.id.iv_triangle)

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
            tagLocation.y - DOT_HEIGHT / 2 - TRIANGLE_HEIGHT - TEXT_HEIGHT - tagLocation.topMargin - DOT_TRIANBLE_MARGIN >= 0 -> {
                tagLocation.rectT =
                    tagLocation.y - DOT_HEIGHT / 2 - TRIANGLE_HEIGHT - TEXT_HEIGHT - DOT_TRIANBLE_MARGIN
                tagLocation.typeV = SWOP_DOWN
                ivTriangle.rotationX = 180f
            }
            else -> {
                tagLocation.rectT = tagLocation.y - DOT_HEIGHT / 2
                tagLocation.typeV = SWOP_UP
            }
        }
        resetInnerViewsLocation(tagLocation, parentView, tagView)
    }

    /**
     * 计算Tag内部各个View的位置
     */
    private fun resetInnerViewsLocation(
        tagLocation: TagLocation,
        parentView: ViewGroup,
        tagView: View
    ) {
        val tvTagName: TextView = tagView.findViewById(R.id.tv_tag_name)
        val vDot: View = tagView.findViewById(R.id.v_dot)
        val ivTriangle: View = tagView.findViewById(R.id.iv_triangle)
        val dotLayoutParams = vDot.layoutParams as FrameLayout.LayoutParams
        val textLayoutParams = tvTagName.layoutParams as FrameLayout.LayoutParams
        val triangleLayoutParams = ivTriangle.layoutParams as FrameLayout.LayoutParams
        // 圆点dotView在水平方向的位置跟typeH无关，三种类型的计算方式是一样的
        dotLayoutParams.leftMargin =
            (tagLocation.x - tagLocation.rectL - DOT_HEIGHT / 2).toInt()
        // 三角形的水平位置也与类型无关，三种类型的计算方式一样
        triangleLayoutParams.leftMargin =
            (tagLocation.x - tagLocation.rectL - TRIANGLE_WIDTH / 2).toInt()
        when (tagLocation.typeV) {
            SWOP_DOWN -> {
                dotLayoutParams.topMargin =
                    (tagLocation.y - tagLocation.rectT - DOT_HEIGHT / 2).toInt()
                triangleLayoutParams.topMargin = TEXT_HEIGHT.toInt()
                triangleLayoutParams.bottomMargin = DOT_TRIANBLE_MARGIN.toInt()
                textLayoutParams.topMargin = 0
                tagLocation.textRectT = 0f
                tagLocation.textRectB = TEXT_HEIGHT
            }
            else -> {
                dotLayoutParams.topMargin = 0
                triangleLayoutParams.topMargin = (DOT_HEIGHT + DOT_TRIANBLE_MARGIN).toInt()
                val marginTop = DOT_HEIGHT + TRIANGLE_HEIGHT + DOT_TRIANBLE_MARGIN
                textLayoutParams.topMargin = marginTop.toInt()
                tagLocation.textRectT = marginTop
                tagLocation.textRectB = marginTop + TEXT_HEIGHT
            }
        }
        parentView.requestLayout()
    }

    /**
     * 对整个tag外层布局确定位置
     */
    private fun resetLocation(item: TagLocation, itemView: View) {
        val layoutParams: FrameLayout.LayoutParams =
            itemView.layoutParams as FrameLayout.LayoutParams
        layoutParams.leftMargin = item.rectL.toInt()
        layoutParams.topMargin = item.rectT.toInt()
        layoutParams.width = (item.rectR - item.rectL).toInt()
        itemView.layoutParams = layoutParams
    }

    override fun anim(tagLocation: TagLocation, itemView: View) {
        val tvTagName: TextView = itemView.findViewById(R.id.tv_tag_name)
        val vDot: View = itemView.findViewById(R.id.v_dot)
        val ivTriangle: TriangleView = itemView.findViewById(R.id.iv_triangle)
        vDot.visibility = View.VISIBLE
        ivTriangle.visibility = View.VISIBLE
        if (tagLocation.animated) {
            tvTagName.visibility = View.VISIBLE
            return
        }
        tagLocation.animated = true
        val animator = ValueAnimator.ofFloat(0f, 1f)
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                tvTagName.visibility = View.VISIBLE
            }
        })
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            when (tagLocation.typeH) {
                DEVIATION_LEFT -> leftToRightAnim(tagLocation, tvTagName, value)
                DEVIATION_MIDDLE -> middleToLeftAndRightAnim(tagLocation, tvTagName, value)
                else -> rightToLeftAnim(tagLocation, tvTagName, value)
            }
        }
        animator.duration = 500
        animator.start()
    }

    private fun middleToLeftAndRightAnim(
        tagLocation: TagLocation,
        textView: TextView,
        progress: Float
    ) {
        val middle: Int = (tagLocation.getWidth() / 2).toInt()
        textView.layout(
            middle - (middle * progress).toInt(),
            tagLocation.textRectT.toInt(),
            middle + (middle * progress).toInt(),
            tagLocation.textRectB.toInt()
        )
    }

    private fun leftToRightAnim(tagLocation: TagLocation, textView: TextView, progress: Float) {
        textView.layout(
            0,
            tagLocation.textRectT.toInt(),
            (tagLocation.getWidth() * progress).toInt(),
            tagLocation.textRectB.toInt()
        )
    }

    private fun rightToLeftAnim(tagLocation: TagLocation, textView: TextView, progress: Float) {
        textView.layout(
            (tagLocation.getWidth().toInt() - tagLocation.getWidth() * progress).toInt(),
            tagLocation.textRectT.toInt(),
            tagLocation.getWidth().toInt(),
            tagLocation.textRectB.toInt()
        )
    }

}