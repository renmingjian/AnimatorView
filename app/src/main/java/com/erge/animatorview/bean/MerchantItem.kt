package com.erge.animatorview.bean

/**
 * Created by erge 3/31/21 6:23 PM
 */
data class MerchantItem(
    val likes: Int = 0,
    val description: String = "",
    val list: MutableList<TagLocation>,
    val linkName: String,
    val link: String = "",
)
