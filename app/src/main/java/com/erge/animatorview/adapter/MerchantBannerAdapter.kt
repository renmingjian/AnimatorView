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
import com.youth.banner.adapter.BannerAdapter
import org.greenrobot.eventbus.EventBus

class MerchantBannerAdapter(datas: MutableList<MerchantImage>?, val lineView: View) :
    BannerAdapter<MerchantImage, MerchantBannerAdapter.BannerViewHolder>(datas) {

    val viewHolderMap = mutableMapOf<Int, BannerViewHolder>()
    private var prePosition: Int = -1

    private val list = mutableListOf<TagLocationProvider3>()
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
        private val provider = TagLocationProvider3(lineView) {
            Toast.makeText(itemView.context, it.name, Toast.LENGTH_SHORT).show()
        }

        init {
            list.add(provider)

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
                val flContainerLayoutParams = flContainer.layoutParams
                flContainerLayoutParams.width = data.width
                flContainerLayoutParams.height = data.height
                flContainer.layoutParams = flContainerLayoutParams
                flContainer.visibility = View.VISIBLE
                flContainer.removeAllViews()
//                for (tag in data.list) {
//                    provider3.addView(tag, flContainer)
//                }
                provider.addView(data.list, flContainer)
            } else {
                flContainer.visibility = View.GONE
            }
//            if (realPosition == 0) {
//                tagAnim()
//                linkAnim()
//            }
        }

        fun tagAnim() {
            if (data?.list != null && data?.list!!.size > 0) {
                val list = data?.list
                for ((index, value) in list!!.withIndex()) {
                    println("tagAnim")
                    provider.anim(value, flContainer.getChildAt(index))
                }
            }
        }

        fun linkAnim() {
            // 没有link内容，则不显示
            val animated = data?.animated ?: false
            if (animated || TextUtils.isEmpty(data?.linkName) || TextUtils.isEmpty(data?.link)) return
            val lineLocationArray = IntArray(2)
            val linkLocationArray = IntArray(2)

            lineView.getLocationInWindow(lineLocationArray)
            clLink.getLocationInWindow(linkLocationArray)
            if (linkLocationArray[1] + clLink.height / 2 > lineLocationArray[1]) return
            clLink.visibility = View.VISIBLE
            data?.animated = true
            val animator: ObjectAnimator = ObjectAnimator.ofFloat(
                clLink,
                "alpha",
                0f,
                1f
            )
            animator.duration = 500
            animator.start()
        }
    }

    override fun onViewDetachedFromWindow(holder: BannerViewHolder) {
        super.onViewDetachedFromWindow(holder)
        for (provider in list) {
            EventBus.getDefault().unregister(provider)
        }
    }
}