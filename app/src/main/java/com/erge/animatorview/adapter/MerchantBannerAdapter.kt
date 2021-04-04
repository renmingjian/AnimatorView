package com.erge.animatorview.adapter

import android.animation.ObjectAnimator
import android.graphics.Color
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantImage
import com.erge.animatorview.utils.TagHelper
import com.erge.animatorview.utils.TagLocationProvider3
import com.erge.animatorview.utils.Utils
import com.youth.banner.adapter.BannerAdapter


class MerchantBannerAdapter(datas: MutableList<MerchantImage>?) :
    BannerAdapter<MerchantImage, MerchantBannerAdapter.BannerViewHolder>(datas) {

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_merchant_image, parent, false)
        )
    }

    override fun onBindView(
        holder: BannerViewHolder?,
        data: MerchantImage?,
        position: Int,
        size: Int
    ) {
        holder?.bindData(data!!)
    }

    fun anim(position: Int) {
        viewHolder.linkAnim()
    }

    class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_merchant)
        private val tvLink: TextView = itemView.findViewById(R.id.tv_link)
        private val clLink: View = itemView.findViewById(R.id.cl_link)
        private val tagHelper = TagHelper()
        private var data: MerchantImage? = null

        init {
            ZoomHelper.addZoomableView(imageView)
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
            clLink.setOnClickListener {
                Toast.makeText(itemView.context, data?.linkName, Toast.LENGTH_SHORT).show()
            }
        }

        fun bindData(data: MerchantImage) {
            this.data = data
            if (data.list != null && data.list.size > 0) {
                tagHelper.drawTags(imageView, data.list, TagLocationProvider3 {
                    Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show()
                })
            }
            println("position = $adapterPosition, url = ${data.imgUrl}")
            val layoutParams = imageView.layoutParams
            layoutParams.width = data.width
            layoutParams.height = data.height
            imageView.layoutParams = layoutParams
            Glide.with(imageView.context).load(data.imgUrl).into(imageView)
            tvLink.text = data.linkName
        }

        fun linkAnim() {
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                clLink,
                "translationY",
                Utils.dp2px(46f),
                0f,
                -Utils.dp2px(10f),
                0f
            )
            animator.duration = 800
            animator.start()
        }
    }
}