package com.erge.animatorview.utils

import android.graphics.Color
import android.util.Log
import android.view.View
import android.view.ViewGroup
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.view.TagsView

/**
 * Created by erge 3/30/21 3:28 PM
 */
class TagHelper {

    private var targetView: View? = null
    private var parentView: ViewGroup? = null
    private var tagsView: TagsView? = null
    var itemTagClick: (TagLocation) -> Unit = { Log.e("tagView", "itemTagClick")}

    fun drawTags(view: View, data: MutableList<TagLocation>) {
        targetView = view
        parentView = targetView?.parent as ViewGroup
        tagsView = TagsView(targetView!!)
        val index = parentView?.indexOfChild(targetView)
        val layoutParams = view.layoutParams
        if (index == -1) return
//        parentView?.removeView(view)
        parentView?.addView(tagsView, index!! + 1, layoutParams)
        tagsView?.data = data
        tagsView?.itemTagClick = itemTagClick
        tagsView?.setBackgroundColor(Color.parseColor("#11ff0000"))
    }

}