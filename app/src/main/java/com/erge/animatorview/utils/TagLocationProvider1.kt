package com.erge.animatorview.utils

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.LEFT_TO_RIGHT
import com.erge.animatorview.bean.RIGHT_TO_LEFT
import com.erge.animatorview.bean.TagLocation

/**
 * Created by erge 4/1/21 1:41 PM
 */
class TagLocationProvider1(override var itemClick: (TagLocation) -> Unit) : TagLocationProvider {

    override fun addView(tagLocation: TagLocation, parent: ViewGroup) {
        val itemView = LayoutInflater.from(parent.context)
            .inflate(R.layout.item_tag_left_to_right, parent, false)
        itemView.setOnClickListener { itemClick.invoke(tagLocation) }
        val tvTagName = itemView.findViewById<TextView>(R.id.tv_tag_name)
        tvTagName.text = tagLocation.name
        parent.addView(itemView)
        parent.postDelayed({
            calculateLocation(tagLocation, parent, itemView)
            resetLocation(tagLocation, itemView)
        }, 34)
    }

    override fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup, tagView: View) {
        tagLocation.rectT = tagLocation.y - tagView.height / 2
        if (tagLocation.x - tagLocation.offset + tagView.width <= parentView.width) {
            tagLocation.rectL = tagLocation.x - tagLocation.offset
            tagLocation.typeH = LEFT_TO_RIGHT
        } else {
            tagLocation.rectL = tagLocation.x - (tagView.width - tagLocation.offset)
            tagLocation.typeH = RIGHT_TO_LEFT
        }
        shouldReplace(tagLocation, parentView, tagView)
    }

    override fun anim(tagLocation: TagLocation, itemView: View) {
        TODO("Not yet implemented")
    }

    private fun shouldReplace(tagLocation: TagLocation, parentView: ViewGroup, tagView: View) {
        if (tagLocation.typeH == RIGHT_TO_LEFT) {
            parentView.removeView(tagView)
            val itemView = LayoutInflater.from(parentView.context)
                .inflate(R.layout.item_tag_right_to_left, parentView, false)
            itemView.setOnClickListener { itemClick.invoke(tagLocation) }
            val tvTagName = itemView.findViewById<TextView>(R.id.tv_tag_name)
            tvTagName.text = tagLocation.name
            parentView.addView(itemView)
            resetLocation(tagLocation, itemView)
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