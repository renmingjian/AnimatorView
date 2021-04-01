package com.erge.animatorview.view

import android.content.Context
import android.content.res.TypedArray
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import android.widget.ListAdapter
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.TagLocation

/**
 * Created by erge 3/31/21 4:39 PM
 */
class TagsLayout(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private var targetView: View? = null
    private var offset: Float = 0f
    var itemTagClick: (TagLocation) -> Unit = {}
    var adapter: ListAdapter? = null
    var data: MutableList<TagLocation>? = null
        set(value) {
            field = value
            addTags()
        }

    init {
        val ta: TypedArray = context.obtainStyledAttributes(attributeSet, R.styleable.TagsLayout)
        offset = ta.getDimension(R.styleable.TagsLayout_location_offset, 0f)
        ta.recycle()
    }

    override fun onFinishInflate() {
        super.onFinishInflate()
        targetView = getChildAt(0)
    }

    private fun calculateLocation(itemView: View) {
        if (data === null) return
        val itemWidth = itemView.width
        val itemHeight = itemView.height
        println("offset = $offset")
        for (item in data!!) {
            if (item.x - offset + itemWidth <= width) {
                item.rectL = item.x - offset
            } else {
                item.rectL = item.x - (itemWidth - offset)
            }
            item.rectT = item.y - itemHeight / 2
        }
    }

    private fun addTags() {
        for (item in data!!) {
            val itemView = LayoutInflater.from(context).inflate(R.layout.item_tag, this, false)
            itemView!!.setOnClickListener { itemTagClick.invoke(item) }
            val tvTagName = itemView.findViewById<TextView>(R.id.tv_tag_name)
            tvTagName.text = item.name
            addView(itemView)
            postDelayed({
                calculateLocation(itemView)
                resetLocation(item, itemView)
            }, 34)
        }
    }

    private fun resetLocation(item: TagLocation, itemView: View) {
        val layoutParams: LayoutParams = itemView.layoutParams as LayoutParams
        layoutParams.leftMargin = item.rectL.toInt()
        layoutParams.topMargin = item.rectT.toInt()
        println("item-rectL = ${item.rectL}--rectT = ${item.rectT} --name:${item.name} --width = $width --itemW = ${itemView.width}")
        itemView.layoutParams = layoutParams
    }
}