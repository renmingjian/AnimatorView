package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

/**
 * Created by erge 2020-03-20 19:31
 */
public class ChargeProgressView extends View {

    // 画进度线和图标的画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 画灰色背景进度线的画笔
    private Paint mPaintGray = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 电量
    private float progress = 0.0f;
    // 整个View的宽高
    private int mHeight;
    private int mWidth;
    // 模糊渐变
    private LinearGradient gradientBlur;
    // 横向颜色线性渐变
    private LinearGradient gradientLinear;
    // 模糊
    private BlurMaskFilter filter;
    // 开始画线的位置
    private int lineStart;
    // 是否有车，如果无车，则不显示充电的具体电量，默认给一个20%的电量
    private boolean noCar;
    // 模糊闪光灯效果进度
    private float lightProgress = 1f;

    public ChargeProgressView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setStyle(Paint.Style.FILL);
        mPaintGray.setStyle(Paint.Style.FILL);
        mPaintGray.setColor(0x33333843);
        mPaintGray.setStyle(Paint.Style.STROKE);
        lightAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        filter = new BlurMaskFilter(mHeight / 3f, BlurMaskFilter.Blur.NORMAL);

        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(mHeight / 3);
        mPaintGray.setStrokeWidth(mHeight / 3);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();

        Bitmap bitmap = getBitmapByDrawable();
        int bitmapHeight = bitmap.getHeight();
        int bitmapWidth = bitmap.getWidth();

        if (noCar) {
            progress = 0.2f;
            lineStart = 0;
        } else {
            lineStart = mHeight;
        }

        float end = lineStart + (mWidth - lineStart - bitmapWidth) * progress;

        gradientLinear = new LinearGradient(0, mHeight / 3, end, mHeight / 3 * 2, 0xff33FFEC, 0xff1FFF6D, Shader.TileMode.CLAMP);


        // 画灰色背景
        mPaint.setStrokeWidth(mHeight / 3);
        canvas.drawLine(lineStart, mHeight / 2f, mWidth - bitmapWidth, mHeight / 2f, mPaintGray);

        // 先画模糊
        if (!noCar) {
            mPaint.setStrokeWidth(mHeight / 3f * (0.2f + 0.8f * lightProgress));
            gradientBlur = new LinearGradient(lineStart, 0, end, mHeight, 0xff1FFF6D, 0xff33FFEC, Shader.TileMode.CLAMP);
            mPaint.setShader(gradientBlur);
            mPaint.setMaskFilter(filter);
            mPaint.setStrokeCap(Paint.Cap.ROUND);
            canvas.drawLine(lineStart, mHeight / 2f, end, mHeight / 2f, mPaint);
        }

        // 画进度线
        mPaint.setStrokeWidth(mHeight / 3);
        mPaint.setShader(gradientLinear);
        mPaint.setMaskFilter(null);
        mPaint.setStrokeCap(Paint.Cap.SQUARE);
        canvas.drawLine(lineStart, mHeight / 2f, end, mHeight / 2f, mPaint);

        // 画充电图标
        mPaint.setShader(null);
        mPaint.setMaskFilter(null);

        canvas.drawBitmap(getBitmapByDrawable(), end - Utils.dp2px(3), (mHeight - bitmapHeight) / 2, mPaint);

        canvas.restore();
    }

    private Bitmap getBitmapByDrawable() {
        Bitmap bitmap = null;
        Drawable drawable = getContext().getDrawable(R.mipmap.icon_charge);
        if (drawable instanceof BitmapDrawable) {
            bitmap = ((BitmapDrawable) drawable).getBitmap();
        }
        return bitmap;
    }

    public void setProgress(int progress) {
        noCar = progress <= 0;
        this.progress = progress / 100f;
        invalidate();
    }

    public void setLightProgress(float lightProgress) {
        this.lightProgress = lightProgress;
        invalidate();
    }

    private void lightAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "lightProgress", 0f, 1f);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.setInterpolator(new LinearInterpolator());
        animator.start();
    }
}
