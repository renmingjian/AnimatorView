package com.erge.animatorview.utils

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.erge.animatorview.bean.TagLocation

/**
 * 给定一个需要添加标签的View，可以是任何View，并且给出全部数据，比如View的宽和高（如果对应到图片，则我图片的宽高），再给
 * 出标签在View中的位置，即可给View添加一些列标签
 * Created by erge 3/30/21 3:28 PM
 */
class TagHelper {

    private var targetView: View? = null
    private var parentView: ViewGroup? = null
    private var tagsView: FrameLayout? = null
    private var data: MutableList<TagLocation>? = null
    private var provider: TagLocationProvider? = null

    /**
     * 拿到targetView的parent，在他上面添加一个FrameLayout，该FrameLayout用来盛放所有的标签。并且给FrameLayout
     * 设置大小和targetView一样，其实就是覆盖在targetView上
     */
    fun drawTags(view: View, data: MutableList<TagLocation>?, provider: TagLocationProvider) {
        if (data == null) {
            if (tagsView != null) {
                parentView?.removeView(tagsView)
            }
        } else {
            targetView = view
            this.data = data
            this.provider = provider
            parentView = targetView?.parent as ViewGroup
            if (tagsView != null) parentView?.removeView(tagsView)
            tagsView = FrameLayout(view.context)
            val index = parentView?.indexOfChild(targetView)
            val layoutParams = view.layoutParams
            if (index == -1) return
            parentView?.addView(tagsView, index!! + 1, layoutParams)
            addTagViews()

            tagsView?.setBackgroundColor(Color.parseColor("#44ff0000"))
        }
    }

    fun startAnim(tagList: MutableList<TagLocation>?) {
        if (tagList != null && tagList.size > 0) {
            for ((index, tagLocation) in tagList.withIndex()) {
                println("tagsView = $tagsView")
                provider?.anim(tagLocation, tagsView?.getChildAt(index)!!)
            }
        }
    }

    private fun addTagViews() {
        tagsView?.removeAllViews()
        for (item in data!!) {
            provider?.addView(item, tagsView!!)
        }
    }

    fun setTagsInvisible() {
        tagsView?.visibility = View.INVISIBLE
    }

    fun setTagsVisible() {
        tagsView?.visibility = View.VISIBLE
    }

}