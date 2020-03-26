package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.MaskFilter;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

/**
 * 充电背景呼吸灯
 * Created by erge 2020-03-24 09:37
 */
public class ChargeBgView extends View {

    private static final int COLOR = 0xff153925;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float progress;
    private MaskFilter maskFilter = new BlurMaskFilter(Utils.dp2px(30), BlurMaskFilter.Blur.NORMAL);
    private int realSize;

    public ChargeBgView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setDither(true);

        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(maskFilter);
        startAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        realSize = MeasureSpec.getSize(widthMeasureSpec);
        setMeasuredDimension(resolveSize(realSize, widthMeasureSpec), resolveSize(realSize, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setColor(0xff14171D);
        canvas.drawRect(0, 0, realSize, realSize, mPaint);
        mPaint.setColor(COLOR);
        canvas.drawOval((1 - progress) * realSize + realSize / 10f, realSize / 2f * progress,
                realSize * progress - realSize / 12f, realSize / 6f * 5 * progress, mPaint);
        canvas.restore();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

    private void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0.97f, 0.90f);
        animator.setDuration(2000);
        animator.setRepeatCount(-1);
        animator.setRepeatMode(ValueAnimator.REVERSE);
        animator.start();
    }

}
