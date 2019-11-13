package com.erge.animatorview.view

import android.animation.ObjectAnimator
import android.animation.ValueAnimator
import android.content.Context
import android.graphics.*
import android.util.AttributeSet
import android.view.View
import com.erge.animatorview.R
import com.erge.animatorview.utils.Utils
import kotlin.random.Random

/**
 * Created by erge 2019-11-05 09:28
 */
class SkyGalaxyView(context: Context, attributeSet: AttributeSet) : View(context, attributeSet) {

    private val DOT_RADIUS: Float = Utils.dp2px(2f)
    private val LINE_LENGTH: Float = Utils.dp2px(200f)

    private val mPaintBg: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintDot: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintLine: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mPaintBitmap: Paint = Paint(Paint.ANTI_ALIAS_FLAG)
    private val mDotLocations = ArrayList<Point>()
    private val mLineLocations = ArrayList<Point>()
    private var mOffsetY: Int = 0
    private var mScreenWidth: Int = 0
    private var mScreenHeight: Int = 0
    private var translateLine: Float = 0f
        set(value) {
            field = value
            invalidate()
        }

    init {
        mScreenWidth = context.resources.displayMetrics.widthPixels
        mScreenHeight = context.resources.displayMetrics.heightPixels

        for (i in 0 until 50) {
            val x = Random.nextInt(0, mScreenWidth)
            val y = Random.nextInt(0, mScreenHeight)
            mDotLocations.add(Point(x, y))
        }
        produceRandomLine()
    }

    override fun onMeasure(widthMeasureSpec: Int, heightMeasureSpec: Int) {
        val width: Int = MeasureSpec.getSize(widthMeasureSpec)
        val height: Int = MeasureSpec.getSize(heightMeasureSpec)
        setMeasuredDimension(
            resolveSize(width, widthMeasureSpec),
            resolveSize(height, heightMeasureSpec)
        )
    }

    override fun onDraw(canvas: Canvas?) {
        canvas?.save()
        drawBackground(canvas)
        drawDots(canvas)
        drawLines(canvas)
        drawBitmap(canvas)
        canvas?.restore()
    }

    private fun drawBackground(canvas: Canvas?) {
        mPaintBg.color = Color.parseColor("#000000")
        mPaintBg.style = Paint.Style.FILL
        canvas?.drawRect(0f, 0f, width.toFloat(), height.toFloat(), mPaintBg)
    }

    private fun drawDots(canvas: Canvas?) {
        mPaintDot.color = 0xffffffff.toInt()
        mPaintDot.style = Paint.Style.FILL

        for (index in mDotLocations.indices) {
            if (index % 3 == 0) {
                mPaintDot.maskFilter = null
            } else {
                mPaintDot.maskFilter = BlurMaskFilter(DOT_RADIUS, BlurMaskFilter.Blur.NORMAL)
            }
            canvas?.drawCircle(
                mDotLocations[index].x.toFloat(), mDotLocations[index].y.toFloat(),
                DOT_RADIUS, mPaintDot
            )
        }
    }

    private fun drawLines(canvas: Canvas?) {
        mPaintLine.color = 0xffffffff.toInt()
        mPaintLine.style = Paint.Style.FILL
        mPaintLine.strokeWidth = Utils.dp2px(2f)
        for (index in mLineLocations.indices) {
            val startX: Float = mLineLocations[index].x.toFloat() + translateLine
            val startY: Float = mLineLocations[index].y.toFloat()
            val endX: Float = startX + LINE_LENGTH
            val endY: Float = startY + mOffsetY
            mPaintLine.shader = LinearGradient(
                startX, startY, endX, endY, 0x337EC8DF,
                0xff7EC8DF.toInt(), Shader.TileMode.CLAMP
            )
            canvas?.drawLine(startX, startY, endX, endY, mPaintLine)
        }
    }

    private fun drawBitmap(canvas: Canvas?) {
        val bitmapSun = BitmapFactory.decodeResource(resources, R.drawable.sun)
        val bitmapEarth = BitmapFactory.decodeResource(resources, R.drawable.earth)
        val bitmapMoon = BitmapFactory.decodeResource(resources, R.drawable.moon)
        canvas?.drawBitmap(bitmapSun, 0f, 0f, mPaintBitmap)
    }

    private fun produceRandomLine() {
        mLineLocations.clear()
        for (i in 0 until 5) {
            val x = Random.nextInt(0, mScreenWidth / 2)
            val y = Random.nextInt(0, mScreenHeight)
            mLineLocations.add(Point(x, y))
        }
        mOffsetY = Random.nextInt(0, 100)
    }

    fun startAnim() {
        val objectAnimator = ObjectAnimator.ofFloat(
            this, "translateLine",
            0f, mScreenHeight.toFloat()
        )
        objectAnimator.duration = 3000
        objectAnimator.addUpdateListener { animation: ValueAnimator? ->
            val value: Float = animation?.animatedValue as Float
            if (value > mScreenHeight - 1) {
                produceRandomLine()
            }
        }
        objectAnimator.repeatCount = -1
        objectAnimator.start()
    }

}