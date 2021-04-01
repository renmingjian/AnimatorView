package com.erge.animatorview.view

import android.annotation.SuppressLint
import android.content.Context
import android.view.View
import android.widget.FrameLayout
import android.widget.ListAdapter
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.utils.TagLocationProvider
import com.erge.animatorview.utils.TagLocationProvider1

/**
 * Created by erge 3/31/21 4:39 PM
 */
@SuppressLint("ViewConstructor")
class TagsLayout(context: Context) : FrameLayout(context) {

    var itemTagClick: (TagLocation) -> Unit = {}
    private var provider: TagLocationProvider = TagLocationProvider1(itemTagClick)
    var adapter: ListAdapter? = null
    var data: MutableList<TagLocation>? = null
        set(value) {
            field = value
            addTags()
        }

    private fun calculateLocation(item: TagLocation, itemView: View) {
        provider.calculateLocation(item, this, itemView)
//        if (provider.shouldReplace(item, this, itemView)) {
//            replaceTag(item ,itemView)
//        }
    }

    private fun addTags() {
        removeAllViews()
//        for (item in data!!) {
//            val itemView = provider.getView(this)
//            itemView.setOnClickListener { itemTagClick.invoke(item) }
//            val tvTagName = itemView.findViewById<TextView>(R.id.tv_tag_name)
//            tvTagName.text = item.name
//            addView(itemView)
//            postDelayed({
//                calculateLocation(item, itemView)
//                resetLocation(item, itemView)
//            }, 34)
//        }
    }

    private fun replaceTag(item: TagLocation, view: View) {
        removeView(view)
        val itemView = provider.getViewReplace(this)
        itemView.setOnClickListener { itemTagClick.invoke(item) }
        val tvTagName = itemView.findViewById<TextView>(R.id.tv_tag_name)
        tvTagName.text = item.name
        addView(itemView)
        resetLocation(item, view)
    }

    private fun resetLocation(item: TagLocation, itemView: View) {
        val layoutParams: LayoutParams = itemView.layoutParams as LayoutParams
        layoutParams.leftMargin = item.rectL.toInt()
        layoutParams.topMargin = item.rectT.toInt()
        itemView.layoutParams = layoutParams
    }
}