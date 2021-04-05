package com.erge.animatorview.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.recyclerview.widget.RecyclerView
import androidx.viewpager2.widget.ViewPager2
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantImage
import com.erge.animatorview.bean.MerchantItem
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener

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

        private var data: MerchantItem? = null
        private val tvLike: TextView = itemView.findViewById(R.id.tv_like)
        private val tvDesc: TextView = itemView.findViewById(R.id.tv_desc)
        private val ivMerchant: ImageView = itemView.findViewById(R.id.iv_merchant)
        private var adapter: MerchantBannerAdapter? = null
        private val imageList: MutableList<MerchantImage> = mutableListOf()
        private var currentImageIndex = 0

        private val bannerMerchant: Banner<MerchantImage, MerchantBannerAdapter> =
            itemView.findViewById(R.id.banner_merchant)

        init {
            bannerMerchant.addOnPageChangeListener(object : OnPageChangeListener {
                override fun onPageScrolled(
                    position: Int,
                    positionOffset: Float,
                    positionOffsetPixels: Int
                ) {

                }

                override fun onPageSelected(position: Int) {
//                    adapter!!.anim(position)
                    currentImageIndex = position
                    println("onPageScrollStateChanged-position = $position")
                }

                override fun onPageScrollStateChanged(state: Int) {
                    println("onPageScrollStateChanged = $state")
                    if (state == ViewPager2.SCROLL_STATE_IDLE) {
                        adapter?.anim(currentImageIndex)
                    }
                }
            })
            adapter = MerchantBannerAdapter(imageList)
            bannerMerchant.setAdapter(adapter, false)
        }

        fun bindData(data: MerchantItem) {
            this.data = data
            imageList.clear()
            imageList.addAll(data.imgList)

            tvLike.text = "${data.likes} likes"
            tvDesc.text = data.description
            ivMerchant.setOnClickListener {
                Toast.makeText(itemView.context, "图片点击", Toast.LENGTH_SHORT).show()
            }
            itemView.postDelayed({
                resetBannerSize()
                bannerMerchant.setStartPosition(0)
                adapter?.notifyDataSetChanged()
                adapter?.anim(0)
            }, 17)
        }

        private fun resetBannerSize() {
            data?.let {
                it.resizeBanner(itemView.width)
                val layoutParams = bannerMerchant.layoutParams
                layoutParams.height = it.height
                bannerMerchant.layoutParams = layoutParams
            }
        }
    }
}