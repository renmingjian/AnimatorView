package com.erge.animatorview.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.FrameLayout
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.constraintlayout.widget.ConstraintSet
import com.airbnb.lottie.LottieAnimationView
import com.erge.animatorview.R

/**
 * Created by erge 3/18/21 6:25 PM
 */
class LoadingButton3(context: Context, attributeSet: AttributeSet) :
    FrameLayout(context, attributeSet) {

    private val constraintLayout: ConstraintLayout
    private val gradientView: GradientButton
    private val lottieView: LottieAnimationView
    private val constraintSet: ConstraintSet = ConstraintSet()
    private val loadingLineSize: Float
    var onLoadingListener: () -> Unit = {}

    init {
        val view: View = LayoutInflater.from(context).inflate(R.layout.layout_loading_button, this)
        constraintLayout = view.findViewById(R.id.constraintLayout)
        gradientView = view.findViewById(R.id.gradientView)
        lottieView = view.findViewById(R.id.lottieView)
        constraintSet.clone(constraintLayout)

        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.LoadingButton)
        val text = typedArray.getString(R.styleable.LoadingButton_text)
        val gradientStyle = typedArray.getInt(R.styleable.LoadingButton_gradientStyle, 1)
        val textColor =
            typedArray.getColor(R.styleable.LoadingButton_text_color, Color.parseColor("#ffffff"))
        val textSize = typedArray.getDimension(R.styleable.LoadingButton_text_size, 14f)
        val fillColor =
            typedArray.getColor(R.styleable.LoadingButton_fill_color, Color.parseColor("#ffd700"))
        val cornerSize = typedArray.getDimension(R.styleable.LoadingButton_corner_size, 0f)
        loadingLineSize =
            typedArray.getDimension(R.styleable.LoadingButton_loading_line_size, 0f)

        gradientView.text = text
        gradientView.cornerSize = cornerSize
        gradientView.fillColor = fillColor
        gradientView.textColor = textColor
        gradientView.textSize = textSize
        gradientView.gradientStyle = gradientStyle

        gradientView.onLoadingListener = {
            reConstraint(gradientStyle)
            confirmLottieViewSize()
            lottieView.visibility = View.VISIBLE
            lottieView.playAnimation()
            onLoadingListener.invoke()
        }
    }

    /**
     * 重置状态
     */
    fun resetState() {
        lottieView.pauseAnimation()
        lottieView.visibility = View.INVISIBLE
        gradientView.state = GradientButton.State.NORMAL
    }

    private fun confirmLottieViewSize() {
        val layoutParams = lottieView.layoutParams
        val size = if (loadingLineSize == 0f) measuredHeight / 2f else loadingLineSize
        layoutParams.width = size.toInt()
        layoutParams.height = size.toInt()
        lottieView.layoutParams = layoutParams
    }

    private fun reConstraint(gradientStyle: Int) {
        val margin = if (loadingLineSize == 0f) measuredHeight / 4 else loadingLineSize / 2
        constraintSet.connect(
            R.id.lottieView,
            ConstraintSet.TOP,
            ConstraintSet.PARENT_ID,
            ConstraintSet.TOP,
            0
        )
        constraintSet.connect(
            R.id.lottieView,
            ConstraintSet.BOTTOM,
            ConstraintSet.PARENT_ID,
            ConstraintSet.BOTTOM,
            0
        )
        when (gradientStyle) {
            0 -> {
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    margin.toInt()
                )
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.END,
                    ConstraintSet.UNSET,
                    ConstraintSet.END,
                    0
                )
            }
            1 -> {
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.START,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.START,
                    0
                )
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    0
                )
            }
            2 -> {
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.END,
                    ConstraintSet.PARENT_ID,
                    ConstraintSet.END,
                    margin.toInt()
                )
                constraintSet.connect(
                    R.id.lottieView,
                    ConstraintSet.START,
                    ConstraintSet.UNSET,
                    ConstraintSet.START,
                    0
                )
            }
        }

        constraintSet.applyTo(constraintLayout)
    }

}