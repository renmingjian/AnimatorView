package com.erge.animatorview.utils

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.erge.animatorview.R
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
    private val DOT_TRIANBLE_MARGIN = Utils.dp2px(4f)
    private lateinit var tagLocation: TagLocation
    private lateinit var parentView: ViewGroup


    override fun addView(tagLocation: TagLocation, parent: ViewGroup) {
        this.tagLocation = tagLocation
        this.parentView = parent
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag4, parent, false) as FrameLayout
        itemView.setOnClickListener { itemClick.invoke(tagLocation) }
        val tvTagName: TextView = itemView.findViewById(R.id.tv_tag_name)
        tvTagName.text = tagLocation.name
        parent.addView(itemView)

        parent.postDelayed({
            calculateLocation(tagLocation, parent, itemView)
            resetLocation(tagLocation, itemView)
        }, 17)
    }

    override fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup, tagView: View) {
        val parentWidth = parentView.width
        val parentHeight = parentView.height
        val itemWidth = tagView.width

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
        if (tagLocation.animated) return
        tagLocation.animated = true
        val tvTagName: TextView = itemView.findViewById(R.id.tv_tag_name)
        itemView.postDelayed( {

        }, 34)

        when (tagLocation.typeH) {
            DEVIATION_LEFT -> leftToRightAnim(tvTagName)
            DEVIATION_MIDDLE -> middleToLeftAndRightAnim(tvTagName)
            else -> rightToLeftAnim(tvTagName)
        }
    }

    private fun middleToLeftAndRightAnim(textView: TextView) {
        val layoutParams = textView.layoutParams
        val animator = ValueAnimator.ofFloat(0f, textView.width.toFloat())
        animator.duration = 500
        animator.addUpdateListener {
            val value = it.animatedValue as Float
            layoutParams.width = value.toInt()
            textView.layoutParams = layoutParams
        }
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                textView.visibility = View.VISIBLE
            }
        })
        animator.start()
    }

    private fun leftToRightAnim(textView: TextView) {
        val animator: ObjectAnimator = ObjectAnimator.ofFloat(
            textView,
            "translationX",
            -textView.width.toFloat(),
            0f
        )
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                textView.visibility = View.VISIBLE
            }
        })
        animator.duration = 500
        animator.start()

    }

    private fun rightToLeftAnim(textView: TextView) {
        val animator: ObjectAnimator = ObjectAnimator.ofFloat(
            textView,
            "translationX",
            textView.width.toFloat(),
            0f
        )
        animator.addListener(object : AnimatorListenerAdapter() {
            override fun onAnimationStart(animation: Animator?) {
                super.onAnimationStart(animation)
                textView.visibility = View.VISIBLE
            }
        })
        animator.duration = 500
        animator.start()
    }

}