package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;


/**
 * Created by erge 2019-10-23 20:08
 */
public class CircleLoadingView extends View {

    private static final float RING_WIDTH = Utils.dp2px(3);
    private static final int COLORS[] = {
            Color.parseColor("#ff0000"),
            Color.parseColor("#00ff00"),
            Color.parseColor("#0000ff"),
    };

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float mRadius;
    private float progress;
    private int mIndex;
    private int mCurrentColor = COLORS[0];

    public CircleLoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setDither(true);
        mPaint.setStrokeWidth(RING_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        anim();
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
        mRadius = getHeight() / 2;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setColor(mCurrentColor);
        RectF rectF = new RectF(getWidth() / 2 - mRadius + RING_WIDTH / 2, getHeight() / 2 - mRadius + RING_WIDTH / 2,
                getWidth() / 2 + mRadius - RING_WIDTH / 2, getHeight() / 2 + mRadius - RING_WIDTH / 2);
        canvas.drawArc(rectF, 90, 360 * progress, false, mPaint);
//        canvas.drawCircle(getWidth() / 2, getHeight() / 2, mRadius, mPaint);

        canvas.restore();
    }

    private void anim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofFloat(this, "progress", 0f, 1f);
        objectAnimator.setDuration(1000);
        objectAnimator.setInterpolator(new LinearInterpolator());
        objectAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float progress = (float) animation.getAnimatedValue();
                if (progress > 0.98f) {
                    mIndex++;
                    mCurrentColor = COLORS[mIndex % 3];
                }
            }
        });
        objectAnimator.setRepeatCount(-1);
        objectAnimator.start();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
