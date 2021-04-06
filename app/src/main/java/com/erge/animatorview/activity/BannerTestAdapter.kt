package com.erge.animatorview.activity

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.youth.banner.adapter.BannerAdapter


/**
 * Created by erge 4/6/21 10:38 AM
 */
class BannerTestAdapter(data: MutableList<String>?) :
    BannerAdapter<String, BannerTestAdapter.ViewHolder>(data) {

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        val imageView: ImageView = itemView as ImageView
        fun bind(data: String) {
            Glide.with(itemView.context).load(data).into(imageView)
        }
    }

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val imageView = ImageView(parent.context)
        imageView.setLayoutParams(
            ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT
            )
        )
        imageView.setScaleType(ImageView.ScaleType.CENTER_CROP)
        return ViewHolder(imageView)
    }

    override fun onBindView(holder: ViewHolder?, data: String?, position: Int, size: Int) {
        holder?.bind(data!!)
    }
}