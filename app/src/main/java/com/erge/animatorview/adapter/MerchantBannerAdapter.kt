package com.erge.animatorview.adapter

import android.animation.ObjectAnimator
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import com.aghajari.zoomhelper.ZoomHelper
import com.bumptech.glide.Glide
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantImage
import com.erge.animatorview.utils.TagHelper
import com.erge.animatorview.utils.TagLocationProvider3
import com.erge.animatorview.utils.Utils
import com.erge.animatorview.view.TagLayout
import com.youth.banner.adapter.BannerAdapter

class MerchantBannerAdapter(datas: MutableList<MerchantImage>?) :
    BannerAdapter<MerchantImage, MerchantBannerAdapter.BannerViewHolder>(datas) {

    val viewHolderMap = mutableMapOf<Int, BannerViewHolder>()
    private var prePosition: Int = -1

    override fun onCreateHolder(parent: ViewGroup, viewType: Int): BannerViewHolder {
        return BannerViewHolder(
            LayoutInflater.from(parent.context).inflate(R.layout.item_merchant_image, parent, false)
        )
    }

    override fun onBindView(
        holder: BannerViewHolder,
        data: MerchantImage,
        position: Int,
        size: Int
    ) {
        viewHolderMap[position] = holder
        holder.bindData(data)
    }

    fun anim(currentPosition: Int) {
        if (currentPosition == prePosition) return
        val preHolder = viewHolderMap[prePosition]
        val currentHolder = viewHolderMap[currentPosition]
        currentHolder?.linkAnim()
        currentHolder?.tagAnim()
        prePosition = currentPosition
    }

    inner class BannerViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        private val imageView: ImageView = view.findViewById(R.id.iv_merchant)
        private val tvLink: TextView = itemView.findViewById(R.id.tv_link)
        private val clLink: View = itemView.findViewById(R.id.cl_link)
        private val flContainer: FrameLayout = itemView.findViewById(R.id.fl_container)
        private val tagHelper = TagHelper()
        private var data: MerchantImage? = null
        private val provider3 = TagLocationProvider3 {

        }

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
            val realPosition = getRealPosition(adapterPosition)
            val layoutParams = imageView.layoutParams
            layoutParams.width = data.width
            layoutParams.height = data.height
            imageView.layoutParams = layoutParams

            Glide.with(imageView.context).load(data.imgUrl).into(imageView)
            if (TextUtils.isEmpty(data.linkName) || TextUtils.isEmpty(data.link)) {
                clLink.visibility = View.GONE
            } else {
                if (data.animated) {
                    clLink.visibility = View.VISIBLE
                } else {
                    clLink.visibility = View.INVISIBLE
                }
            }
            tvLink.text = data.linkName
            if (data.list != null && data.list.size > 0) {
                // 设置tag
//                tagHelper.drawTags(imageView, data.list, TagLocationProvider3 {
//                    Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show()
//                })
//
//                if (realPosition == 0) {
//                    itemView.postDelayed({
//                        tagAnim()
//                    }, 17)
//                }
//                tagHelper.setTagsVisible()
                val flContainerLayoutParams = flContainer.layoutParams
                flContainerLayoutParams.width = data.width
                flContainerLayoutParams.height = data.height
                flContainer.layoutParams = flContainerLayoutParams
                flContainer.visibility = View.VISIBLE
                flContainer.removeAllViews()
                for (tag in data.list) {
                    provider3.addView(tag, flContainer)
                }
                println("child = ${flContainer.childCount}")
            } else {
                flContainer.visibility = View.GONE
//                tagHelper.setTagsInvisible()
            }
            if (realPosition == 0) {
                tagAnim()
                linkAnim()
            }
            println("real-position = ${getRealPosition(adapterPosition)}")
        }

        fun tagAnim() {
            if (data?.list != null && data?.list!!.size > 0) {
//                tagHelper.startAnim(data?.list)
                val list = data?.list
                for ((index, value) in list!!.withIndex()) {
                    provider3.anim(value, flContainer.getChildAt(index))
                }
            }
        }

        fun linkAnim() {
            // 没有link内容，则不显示
            val animated = data?.animated ?: false
            println("animated = $animated, position = ${getRealPosition(adapterPosition)}")
            if (animated || TextUtils.isEmpty(data?.linkName) || TextUtils.isEmpty(data?.link)) return
            clLink.visibility = View.VISIBLE
            data?.animated = true
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                clLink,
                "translationY",
                Utils.dp2px(46f),
                0f,
                -Utils.dp2px(5f),
                0f
            )
            animator.duration = 800
            animator.start()
        }
    }
}