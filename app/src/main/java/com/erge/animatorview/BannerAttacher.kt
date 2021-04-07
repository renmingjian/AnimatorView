package com.erge.animatorview

import androidx.viewpager2.widget.ViewPager2
import com.erge.animatorview.adapter.MerchantBannerAdapter
import com.erge.animatorview.bean.MerchantImage
import com.youth.banner.Banner
import com.youth.banner.listener.OnPageChangeListener
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

/**
 * Created by erge 4/6/21 7:27 PM
 */
class BannerAttacher(val count: Int): ScrollingPagerIndicator.PagerAttacher<Banner<MerchantImage, MerchantBannerAdapter>> {

    private lateinit var banner: Banner<MerchantImage, MerchantBannerAdapter>

    override fun attachToPager(
        indicator: ScrollingPagerIndicator,
        pager: Banner<MerchantImage, MerchantBannerAdapter>
    ) {
        banner = pager
        updateIndicatorDotsAndPosition(indicator)
        banner.addOnPageChangeListener(object : OnPageChangeListener {

            var idleState: Boolean = true

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updateIndicatorOnPagerScrolled(indicator, position, positionOffset)
            }

            override fun onPageSelected(position: Int) {
                if (idleState) {
                    updateIndicatorDotsAndPosition(indicator)
                }
            }

            override fun onPageScrollStateChanged(state: Int) {
                idleState = state == ViewPager2.SCROLL_STATE_IDLE
            }

        })
    }

    override fun detachFromPager() {

    }

    private fun updateIndicatorDotsAndPosition(indicator: ScrollingPagerIndicator) {
        indicator.setDotCount(count)
        indicator.setCurrentPosition(banner.currentItem)
    }

    fun updateIndicatorOnPagerScrolled(
        indicator: ScrollingPagerIndicator,
        position: Int,
        positionOffset: Float
    ) {
        var offset: Float = 0f
        offset = if (positionOffset < 0) {
            0f
        } else if (positionOffset > 1) {
            1f
        } else {
            positionOffset
        }
        indicator.onPageScrolled(position, offset)
    }

}