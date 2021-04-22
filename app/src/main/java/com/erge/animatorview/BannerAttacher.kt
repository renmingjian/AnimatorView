package com.erge.animatorview

import androidx.viewpager.widget.ViewPager
import androidx.viewpager2.widget.ViewPager2
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

/**
 * Created by erge 4/6/21 7:27 PM
 */
class BannerAttacher(val count: Int, val pageListener: ViewPager.OnPageChangeListener) :
    ScrollingPagerIndicator.PagerAttacher<ViewPager> {

    private lateinit var banner: ViewPager

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

    override fun attachToPager(indicator: ScrollingPagerIndicator, pager: ViewPager) {
        banner = pager
        updateIndicatorDotsAndPosition(indicator)
        banner.addOnPageChangeListener(object : ViewPager.OnPageChangeListener {

            var idleState: Boolean = true

            override fun onPageScrolled(
                position: Int,
                positionOffset: Float,
                positionOffsetPixels: Int
            ) {
                updateIndicatorOnPagerScrolled(indicator, position, positionOffset)
                pageListener.onPageScrolled(position, positionOffset, positionOffsetPixels)
            }

            override fun onPageSelected(position: Int) {
                if (idleState) {
                    updateIndicatorDotsAndPosition(indicator)
                }
                pageListener.onPageSelected(position)
            }

            override fun onPageScrollStateChanged(state: Int) {
                idleState = state == ViewPager2.SCROLL_STATE_IDLE
                pageListener.onPageScrollStateChanged(state)
            }

        })
    }

}