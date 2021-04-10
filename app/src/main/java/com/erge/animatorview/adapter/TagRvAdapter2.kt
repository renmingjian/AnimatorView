package com.erge.animatorview.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import androidx.viewpager2.widget.ViewPager2
import com.airbnb.lottie.LottieAnimationView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.erge.animatorview.BannerAttacher
import com.erge.animatorview.R
import com.erge.animatorview.bean.MerchantImage
import com.erge.animatorview.bean.MerchantItem
import com.erge.animatorview.utils.Utils
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener
import org.greenrobot.eventbus.EventBus
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

/**
 * Created by erge 3/31/21 6:17 PM
 */
class TagRvAdapter2(val lineView: View) :
    BaseQuickAdapter<MerchantItem, BaseViewHolder>(R.layout.item_rv_tag) {

    private var currentImageIndex = 0
    val list: SparseArray<MerchantBannerAdapter> = SparseArray()
    override fun convert(holder: BaseViewHolder, item: MerchantItem) {

        val tvLike: TextView = holder.getView(R.id.tv_like)
        val ivLike: ImageView = holder.getView(R.id.iv_like)
        val tvBannerPosition: TextView = holder.getView(R.id.tv_banner_position)
        val tvDesc: TextView = holder.getView(R.id.tv_desc)
        val ivMerchant: ImageView = holder.getView(R.id.iv_merchant)
        val indicator: ScrollingPagerIndicator = holder.getView(R.id.indicator)
        val lottieLike = holder.getView<LottieAnimationView>(R.id.lottieLike)
        val bannerMerchant: Banner<MerchantImage, MerchantBannerAdapter> =
            holder.getView(R.id.banner_merchant)

        ivLike.setImageResource(if (item.currentUserliked) R.drawable.ic_liked else R.drawable.ic_like)

        ivLike.setOnClickListener {
            lottieLike.visibility = View.VISIBLE
//            ivLike.visibility = View.INVISIBLE
            lottieLike.playAnimation()
        }

        lottieLike.addAnimatorListener(object : AnimatorListenerAdapter() {
            override fun onAnimationEnd(animation: Animator?) {
                super.onAnimationEnd(animation)
                lottieLike.visibility = View.INVISIBLE
                ivLike.visibility = View.VISIBLE
                ivLike.setImageResource(R.drawable.ic_liked)
                item.currentUserliked = true
            }
        })

        val adapter = MerchantBannerAdapter(item.imgList, lineView)
        list.put(holder.adapterPosition, adapter)
        bannerMerchant.currentItem = 0
        bannerMerchant.setAdapter(adapter, false)
        resetBannerSize(item, bannerMerchant)
//            bannerMerchant.viewPager2.offscreenPageLimit = data.imgList.size
        tvLike.text = item.getLikeCountByFormat()
        tvDesc.text = item.description
        ivMerchant.setOnClickListener {
            Toast.makeText(context, "图片点击", Toast.LENGTH_SHORT).show()
        }
        if (item.imgList.size > 1) {
            indicator.visibility = View.VISIBLE
            tvBannerPosition.visibility = View.VISIBLE
            indicator.attachToPager(
                bannerMerchant,
                BannerAttacher(item.imgList.size, object : OnPageChangeListener {
                    override fun onPageScrolled(
                        position: Int,
                        positionOffset: Float,
                        positionOffsetPixels: Int
                    ) {

                    }

                    override fun onPageSelected(position: Int) {
                        currentImageIndex = position
                        tvBannerPosition.text = "$position/${item.imgList.size}"
                    }

                    override fun onPageScrollStateChanged(state: Int) {
                        if (state == ViewPager2.SCROLL_STATE_IDLE) {
                            adapter.anim(currentImageIndex)
                        }
                    }
                })
            )
        } else {
            indicator.visibility = View.GONE
            tvBannerPosition.visibility = View.GONE
        }
    }

    private fun resetBannerSize(
        data: MerchantItem,
        bannerMerchant: Banner<MerchantImage, MerchantBannerAdapter>
    ) {
        data.resizeBanner((Utils.getScreen(context).right - Utils.dp2px(20f)).toInt())
        val layoutParams = bannerMerchant.layoutParams
        layoutParams.height = data.height
        bannerMerchant.layoutParams = layoutParams
    }

    fun scrollStopped(firstVisiblePosition: Int, lastVisiblePosition: Int) {
        println("firstVisiblePosition = $firstVisiblePosition, lastVisiblePosition = $lastVisiblePosition")
        for (i in firstVisiblePosition..lastVisiblePosition) {
            println("i === $i")
            val adapter = list[i]
            adapter.anim(currentImageIndex)
        }
    }

}