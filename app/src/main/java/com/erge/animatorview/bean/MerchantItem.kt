package com.erge.animatorview.bean

/**
 * Created by erge 3/31/21 6:23 PM
 */
data class MerchantItem(
    val likes: Int = 0,
    val description: String = "",
    val imgList: MutableList<MerchantImage>,

    var width: Int = 0,
    var height: Int = 0
) {

    /**
     * 设置整个Banner的大小
     */
    fun resizeBanner(viewWidth: Int) {
        var bannerHeight = imgList[0].height

        // 拿到最大高度后，就可以先确定最初始时banner的宽高(此时对比例没有任何限制).之后要考虑最大和最小高度
        if (bannerHeight > 838) {
            bannerHeight = 838
        }
        if (bannerHeight < 536) {
            bannerHeight = 536
        }
        height = bannerHeight
        for (image in imgList) {
            image.resizeImage(viewWidth, bannerHeight)
        }
    }
}

data class MerchantImage(
    var width: Int,
    var height: Int,
    val imgUrl: String,
    val linkName: String,
    val link: String = "",
    val list: MutableList<TagLocation>?,
    var hasSetTags: Boolean = false
) {

    /**
     * 重新设置图片的大小。banner大小确定后，需要确定内部图片的大小，必须要在banner大小范围内。
     */
    fun resizeImage(viewWidth: Int, viewHeight: Int) {
        val parentRadio = viewWidth.toFloat() / viewHeight.toFloat()
        val imageRadio = width.toFloat() / height.toFloat()

        // 重置后图片的真实宽高
        var realWidth = 0
        var realHeight = 0

        if (imageRadio >= parentRadio) {
            realWidth = viewWidth
            realHeight = (realWidth / imageRadio).toInt()
        } else {
            realHeight = viewHeight
            realWidth = (realHeight * imageRadio).toInt()
        }

        // 图片大小重置后，对应内部的tag点的位置也需要重新设置
        if (list !== null && list.size > 0) {
            for (tag in list) {
                tag.relocation(width, height, realWidth, realHeight)
            }
        }
        width = realWidth
        height = realHeight
    }
}
