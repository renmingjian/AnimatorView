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
//        val textView = getTextView(this)
//        var paint = textView?.paint
//        if (paint === null) {
//            paint = TextPaint(Paint.ANTI_ALIAS_FLAG)
//            paint.textSize = Utils.sp2px(12f)
//        }
//
//        for (item in data!!) {
//            // 文字实际所需宽度
//            val textWidth = paint.measureText(item.name)
//            // 如果整个矩形宽度从xy的位置向右伸展会超出View右侧边距，则矩形向左伸展
//            if (item.x + TagsView.MIDDLE_PADDING + textWidth + TagsView.RIGHT_PADDING > targetView!!.width) {
//                item.rectL = item.x - TagsView.MIDDLE_PADDING - textWidth - TagsView.RIGHT_PADDING
//                item.rectR = item.x + TagsView.LEFT_PADDING
//                item.textStart = item.rectL + TagsView.RIGHT_PADDING
//            } else { // 否则矩形向右伸展
//                item.rectL = item.x - TagsView.LEFT_PADDING
//                item.rectR = item.x + TagsView.MIDDLE_PADDING + textWidth + TagsView.RIGHT_PADDING
//                item.textStart = item.x + TagsView.MIDDLE_PADDING
//            }
//            item.rectT = item.y - TagsView.RECT_HEIGHT / 2
//            item.rectB = item.y + TagsView.RECT_HEIGHT / 2
//        }
    }

//    private fun getTextView(view: View): TextView? {
//        val index = 0
//        var indexView = view
//        while (indexView is ViewGroup) {
//            indexView = indexView.getChildAt(index)
//            if (view is TextView) return view
//        }
//        return null
//    }

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