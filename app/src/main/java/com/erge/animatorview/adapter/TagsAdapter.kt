package com.erge.animatorview.adapter

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import com.erge.animatorview.R
import com.erge.animatorview.bean.TagLocation
import com.erge.animatorview.listener.ListAdapter

/**
 * Created by erge 3/31/21 5:08 PM
 */
class TagsAdapter(private var list: List<TagLocation>): ListAdapter {
    override fun getCount(): Int {
        return list.size
    }

    override fun getItem(position: Int): TagLocation {
        return list[position]
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    @SuppressLint("InflateParams")
    override fun getView(position: Int, convertView: View?, parent: ViewGroup?): View {
        var view = convertView
        if (convertView == null) {
            view = LayoutInflater.from(parent!!.context).inflate(R.layout.item_tag, null)
        }
        val tvTagName = view!!.findViewById<TextView>(R.id.tv_tag_name)
        tvTagName.text = getItem(position).name
        return view
    }

}