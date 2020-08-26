package com.erge.animatorview.view;

import android.animation.Keyframe;
import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by erge 2019-09-19 20:43
 */
public class LoadingProgress extends View {

    private float progressStart = 0f;
    private float progressEnd = 0f;
    private Paint mPaint;
    private float mWidth;
    private float mHeight;
    private ObjectAnimator mAnimator;

    public LoadingProgress(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
        startAnim();
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mPaint.setStrokeWidth(mHeight);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#55125652"));
        canvas.drawLine(0, mHeight / 2, mWidth, mHeight / 2, mPaint);
        mPaint.setColor(Color.parseColor("#dd5566"));
        canvas.drawLine(mWidth * progressStart, mHeight / 2,
                mWidth * (progressEnd + 0.2f), mHeight / 2, mPaint);
        canvas.restore();
    }

    private void startAnim() {

        Keyframe keyframeStart1 = Keyframe.ofFloat(0f, 0.0f);
        Keyframe keyframeStart2 = Keyframe.ofFloat(0.2f, 0.2f);
        Keyframe keyframeStart3 = Keyframe.ofFloat(0.8f, 0.9f);
        Keyframe keyframeStart4 = Keyframe.ofFloat(1f, 1f);

        PropertyValuesHolder holder1 = PropertyValuesHolder.ofKeyframe("progressStart", keyframeStart1,
                keyframeStart2, keyframeStart3, keyframeStart4);

        Keyframe keyframeEnd1 = Keyframe.ofFloat(0f, 0f);
        Keyframe keyframeEnd2 = Keyframe.ofFloat(0.2f, 0.6f);
        Keyframe keyframeEnd3 = Keyframe.ofFloat(0.8f, 0.8f);
        Keyframe keyframeEnd4 = Keyframe.ofFloat(1f, 1f);

        PropertyValuesHolder holder2 = PropertyValuesHolder.ofKeyframe("progressEnd", keyframeEnd1,
                keyframeEnd2, keyframeEnd3, keyframeEnd4);

        mAnimator = ObjectAnimator.ofPropertyValuesHolder(this, holder1, holder2);
        mAnimator.setDuration(1500);
        mAnimator.setRepeatCount(-1);
        mAnimator.start();
    }

    @Override
    protected void onVisibilityChanged(@NonNull View changedView, int visibility) {
        super.onVisibilityChanged(changedView, visibility);
        if (mAnimator == null) return;
        if (visibility == View.VISIBLE) {
            mAnimator.start();
        } else {
            mAnimator.pause();
        }
    }

    public float getProgressStart() {
        return progressStart;
    }

    public void setProgressStart(float progressStart) {
        this.progressStart = progressStart;
    }

    public float getProgressEnd() {
        return progressEnd;
    }

    public void setProgressEnd(float progressEnd) {
        this.progressEnd = progressEnd;
        invalidate();
    }
}
