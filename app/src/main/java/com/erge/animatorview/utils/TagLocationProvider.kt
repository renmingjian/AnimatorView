package com.erge.animatorview.utils

import android.view.View
import android.view.ViewGroup
import com.erge.animatorview.bean.TagLocation

/**
 * Created by erge 4/1/21 1:39 PM
 */
interface TagLocationProvider {

    var itemClick: (TagLocation) -> Unit

    fun addView(tagLocation: TagLocation, parent: ViewGroup)

    fun calculateLocation(tagLocation: TagLocation, parentView: ViewGroup, tagView: View)

}