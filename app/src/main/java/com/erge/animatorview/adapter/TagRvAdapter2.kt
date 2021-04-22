package com.erge.animatorview.adapter

import android.animation.Animator
import android.animation.AnimatorListenerAdapter
import android.util.SparseArray
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.viewpager.widget.ViewPager
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
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

/**
 * Created by erge 3/31/21 6:17 PM
 */
class TagRvAdapter2(val lineView: View) :
    BaseQuickAdapter<MerchantItem, BaseViewHolder>(R.layout.item_rv_tag) {

    private var currentImageIndex = 0
    val list: SparseArray<InspirationBannerAdapter2> = SparseArray()
    override fun convert(holder: BaseViewHolder, item: MerchantItem) {

        val tvLike: TextView = holder.getView(R.id.tv_like)
        val ivLike: ImageView = holder.getView(R.id.iv_like)
        val tvBannerPosition: TextView = holder.getView(R.id.tv_banner_position)
        val tvDesc: TextView = holder.getView(R.id.tv_desc)
        val indicator: ScrollingPagerIndicator = holder.getView(R.id.indicator)
        val lottieLike = holder.getView<LottieAnimationView>(R.id.lottieLike)
        val bannerPager = holder.getView<ViewPager>(R.id.banner_pager)

        resetBannerSize(item, bannerPager)

        ivLike.setImageResource(if (item.currentUserliked) R.drawable.ic_liked else R.drawable.ic_like)

        ivLike.setOnClickListener {
            lottieLike.visibility = View.VISIBLE
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

        val adapter = InspirationBannerAdapter2(item.imgList, lineView)
        list.put(holder.adapterPosition, adapter)
        bannerPager.adapter = adapter

        tvLike.text = item.getLikeCountByFormat()
        tvDesc.text = item.description

        if (item.imgList.size > 1) {
            indicator.visibility = View.VISIBLE
            tvBannerPosition.visibility = View.VISIBLE
            indicator.attachToPager(
                bannerPager,
                BannerAttacher(item.imgList.size, object : ViewPager.OnPageChangeListener {
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
                        if (state == ViewPager.SCROLL_STATE_IDLE) {
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
        bannerMerchant: View
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