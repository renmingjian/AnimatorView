package com.erge.animatorview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantItem
import com.erge.animatorview.utils.TagHelper
import com.erge.animatorview.utils.TagLocationProvider3

/**
 * Created by erge 3/31/21 6:17 PM
 */
class TagRvAdapter(val list: MutableList<MerchantItem>) :
    RecyclerView.Adapter<TagRvAdapter.ViewHolder>() {


    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        return ViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_rv_tag, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        holder.bindData(list[position])
    }

    override fun getItemCount(): Int {
        return list.size
    }

    class ViewHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {

        private val tvLike: TextView = itemView.findViewById(R.id.tv_like)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        private val ivMerchant: ImageView = itemView.findViewById(R.id.iv_merchant)
        private val tagHelper = TagHelper()

        init {
            ZoomHelper.getInstance().addOnZoomStateChangedListener(object :
                ZoomHelper.OnZoomStateChangedListener {
                override fun onZoomStateChanged(
                    zoomHelper: ZoomHelper,
                    zoomableView: View,
                    isZooming: Boolean
                ) {
                    if (isZooming) {
                        tagHelper.setTagsInvisible()
                    } else {
                        tagHelper.setTagsVisible()
                    }
                }
            })
        }

        fun bindData(data: MerchantItem) {
            tagHelper.drawTags(ivMerchant, data.list, TagLocationProvider3 {
                Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show()
            })
            tvLike.text = "${data.likes} likes"
            tvDesc.text = data.description
            ivMerchant.setOnClickListener {
                Toast.makeText(itemView.context, "图片点击", Toast.LENGTH_SHORT).show()
            }
        }
    }
}