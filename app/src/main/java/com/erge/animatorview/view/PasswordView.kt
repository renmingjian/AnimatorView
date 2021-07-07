package com.erge.animatorview.view

import android.app.Service
import android.content.Context
import android.graphics.Canvas
import android.graphics.Color
import android.graphics.Paint
import android.graphics.RectF
import android.os.Vibrator
import android.text.Editable
import android.text.TextWatcher
import android.util.AttributeSet
import android.view.View
import android.view.animation.CycleInterpolator
import android.view.animation.TranslateAnimation
import androidx.appcompat.widget.AppCompatEditText
import com.erge.animatorview.R
import com.erge.animatorview.utils.Utils

/**
 * Created by eryemj 2021/6/24
 */
class PasswordView(context: Context, attributeSet: AttributeSet) :
    AppCompatEditText(context, attributeSet), View.OnFocusChangeListener, TextWatcher {

    // rect color
    private var rectNormalColor: Int = Color.parseColor("#e6e8f5")
    private var rectInputtingColor: Int = Color.parseColor("#141c30")
    private var rectErrorColor: Int = Color.parseColor("#ff5844")

    // dot color
    private var dotNormalColor: Int = Color.parseColor("#141c30")
    private var dotInputtingColor: Int = Color.parseColor("#141c30")
    private var dotErrorColor: Int = Color.parseColor("#ff5844")

    // rect and dot size
    private var rectSize = Utils.dp2px(36f)
    private var dotSize = Utils.dp2px(4.5f)
    private var roundSize = Utils.dp2px(6f)

    private var spacing = Utils.dp2px(10f)

    private var length = 6

    private var strokeWidth = Utils.dp2px(1f)

    private val rectPaint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val dotPaint = Paint(Paint.ANTI_ALIAS_FLAG)

    private val rectF = RectF()

    private var currentIndex = 0

    var state: Int = STATE_NORMAL
        set(value) {
            field = value
            if (value == STATE_ERROR) {
                invalidate()
            }
        }

    companion object {
        const val STATE_NORMAL = 0
        const val STATE_INPUTTING = 1
        const val STATE_ERROR = 2
    }

    init {
        onFocusChangeListener = this
        addTextChangedListener(this)
        val typedArray = context.obtainStyledAttributes(attributeSet, R.styleable.PasswordView)
        rectSize = typedArray.getDimension(R.styleable.PasswordView_pw_rect_size, rectSize)
        dotSize = typedArray.getDimension(R.styleable.PasswordView_pw_dot_size, dotSize)
        roundSize = typedArray.getDimension(R.styleable.PasswordView_pw_round_size, roundSize)
        spacing = typedArray.getDimension(R.styleable.PasswordView_pw_spacing, spacing)
        length = typedArray.getInt(R.styleable.PasswordView_pw_length, length)
        rectNormalColor =
            typedArray.getColor(R.styleable.PasswordView_pw_rect_normal_color, rectNormalColor)
        rectInputtingColor = typedArray.getColor(
            R.styleable.PasswordView_pw_rect_inputting_color,
            rectInputtingColor
        )
        rectErrorColor =
            typedArray.getColor(R.styleable.PasswordView_pw_rect_error_color, rectErrorColor)

        dotNormalColor =
            typedArray.getColor(R.styleable.PasswordView_pw_dot_normal_color, dotNormalColor)
        dotInputtingColor = typedArray.getColor(
            R.styleable.PasswordView_pw_dot_inputting_color,
            dotInputtingColor
        )
        dotErrorColor =
            typedArray.getColor(R.styleable.PasswordView_pw_dot_error_color, dotErrorColor)
        typedArray.recycle()
        rectPaint.style = Paint.Style.STROKE
        rectPaint.strokeWidth = strokeWidth
        dotPaint.style = Paint.Style.FILL
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        setMeasuredDimension(
            (rectSize * length + strokeWidth * 2 * length + spacing * (length - 1)).toInt(),
            (rectSize + strokeWidth * 2).toInt()
        )
    }

    override fun onDraw(canvas: Canvas?) {
        println("text: $text, state = $state")
        val textLength = text?.length ?: 0
        currentIndex = textLength - 1
        canvas?.save()
        for (i in 0 until length) {
            if (state == STATE_INPUTTING) {
                if (i == currentIndex) {
                    rectPaint.color = rectInputtingColor
                    dotPaint.color = dotInputtingColor
                } else {
                    rectPaint.color = rectNormalColor
                    dotPaint.color = dotNormalColor
                }
            } else {
                if (state == STATE_NORMAL) {
                    rectPaint.color = rectNormalColor
                    dotPaint.color = dotNormalColor
                } else {
                    rectPaint.color = rectErrorColor
                    dotPaint.color = dotErrorColor
                }
            }
            val left = i * (rectSize + spacing) + strokeWidth + i * 2 * strokeWidth
            rectF.left = left
            rectF.top = strokeWidth
            rectF.right = left + rectSize
            rectF.bottom = rectSize + strokeWidth
            canvas?.drawRoundRect(rectF, roundSize, roundSize, rectPaint)
            if (state != STATE_NORMAL && i <= currentIndex && textLength > 0) {
                canvas?.drawCircle(
                    (rectF.left + rectF.right) / 2f,
                    measuredHeight / 2f,
                    dotSize,
                    dotPaint
                )
            }
        }
        canvas?.restore()
    }

    override fun onFocusChange(v: View?, hasFocus: Boolean) {
        println("onFocusChange: $hasFocus")
    }

    override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {

    }

    override fun afterTextChanged(s: Editable?) {

    }

    override fun onTextChanged(
        text: CharSequence?,
        start: Int,
        lengthBefore: Int,
        lengthAfter: Int
    ) {
        super.onTextChanged(text, start, lengthBefore, lengthAfter)
        state = STATE_INPUTTING
        vibrate()
    }

    private fun vibrate() {
        val vibrate: Vibrator = context.getSystemService(Service.VIBRATOR_SERVICE) as Vibrator
        vibrate.vibrate(30)
    }

}