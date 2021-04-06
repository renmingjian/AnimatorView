package com.erge.animatorview

import com.erge.animatorview.adapter.MerchantBannerAdapter
import com.erge.animatorview.bean.MerchantImage
import com.youth.banner.Banner
import ru.tinkoff.scrollingpagerindicator.ScrollingPagerIndicator

/**
 * Created by erge 4/6/21 7:27 PM
 */
class BannerAttacher: ScrollingPagerIndicator.PagerAttacher<Banner<MerchantImage, MerchantBannerAdapter>> {
    override fun attachToPager(
        indicator: ScrollingPagerIndicator,
        pager: Banner<MerchantImage, MerchantBannerAdapter>
    ) {

    }

    override fun detachFromPager() {

    }
}