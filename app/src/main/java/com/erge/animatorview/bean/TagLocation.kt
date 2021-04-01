package com.erge.animatorview.bean

/**
 * Created by erge 3/30/21 3:38 PM
 */

const val LEFT_TO_RIGHT: Int = 0
const val RIGHT_TO_LEFT: Int = 1
data class TagLocation(

    var x: Float,
    var y: Float,
    var name: String,
    var type: Int = LEFT_TO_RIGHT,

    var rectL: Float = 0f,
    var rectT: Float = 0f,
    var rectR: Float = 0f,
    var rectB: Float = 0f,
    var textX: Float = 0f,

    var textStart: Float = 0f,
)
