package com.erge.animatorview.utils

import android.graphics.Color
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import com.erge.animatorview.bean.TagLocation

/**
 * Created by erge 3/30/21 3:28 PM
 */
class TagHelper {

    private var targetView: View? = null
    private var parentView: ViewGroup? = null
    private var tagsView: FrameLayout? = null
    private var data: MutableList<TagLocation>? = null
    private var provider: TagLocationProvider? = null

    fun drawTags(view: View, data: MutableList<TagLocation>, provider: TagLocationProvider) {
        targetView = view
        this.data = data
        this.provider = provider
        parentView = targetView?.parent as ViewGroup
        if (tagsView === null) {
            tagsView = FrameLayout(view.context)
            val index = parentView?.indexOfChild(targetView)
            val layoutParams = view.layoutParams
            if (index == -1) return
            parentView?.addView(tagsView, index!! + 1, layoutParams)

            addTagViews()
        }
    }

    private fun addTagViews() {
        tagsView?.removeAllViews()
        for (item in data!!) {
            provider?.addView(item, tagsView!!)
        }
    }

}