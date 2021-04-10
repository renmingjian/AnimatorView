package com.erge.animatorview.bean

/**
 * Created by erge 3/30/21 3:38 PM
 */

const val LEFT_TO_RIGHT: Int = 0
const val RIGHT_TO_LEFT: Int = 1
const val TOP_TO_BOTTOM: Int = 3
const val BOTTOM_TO_TOP: Int = 4

data class TagLocation(

    var x: Float,
    var y: Float,
    var name: String,
    var typeH: Int = LEFT_TO_RIGHT,
    var typeV: Int = 0,
    var leftMargin: Float = 0f,
    var topMargin: Float = 0f,
    var rightMargin: Float = 0f,
    var bottomMargin: Float = 0f,
    var offset: Float = 0f,

    var rectL: Float = 0f,
    var rectT: Float = 0f,
    var rectR: Float = 0f,
    var rectB: Float = 0f,

    var textRectL: Float = 0f,
    var textRectT: Float = 0f,
    var textRectR: Float = 0f,
    var textRectB: Float = 0f,

    var textX: Float = 0f,

    var parentWidth: Float = 0f,
    var parentHeight: Float = 0f,

    var textStart: Float = 0f,
    var animated: Boolean = false

) {
    fun relocation(
        originalImageWidth: Int,
        originalImageHeight: Int,
        realImageWidth: Int,
        realImageHeight: Int
    ) {
        parentWidth = realImageWidth.toFloat()
        parentHeight = realImageHeight.toFloat()
        val realX = realImageWidth.toFloat() / originalImageWidth.toFloat() * x
        val realY = realImageHeight.toFloat() / originalImageHeight.toFloat() * y
        x = realX
        y = realY
    }

    fun getWidth(): Float = rectR - rectL
}
