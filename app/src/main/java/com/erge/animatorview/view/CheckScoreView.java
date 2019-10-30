package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.PropertyValuesHolder;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.Locale;

/**
 * Created by erge 2019-09-29 18:21
 */
public class CheckScoreView extends View {

    private static final float DEFAULT_RADIUS = Utils.dp2px(60);
    private static final float DEFAULT_OUT_LAYER_LENGTH = Utils.dp2px(10);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float outLayerRadius;
    private float mRadius;
    private float mOutLayerLength;
    private ObjectAnimator mOutLayerAnim;
    private Paint.FontMetrics mMetrics = new Paint.FontMetrics();
    private String mScore = "待测";

    public CheckScoreView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
        outLayerAnim();
        beat();
    }

    private void initAttrs(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.CheckScoreView);
        mRadius = typedArray.getDimension(R.styleable.CheckScoreView_radius, DEFAULT_RADIUS);
        mOutLayerLength = typedArray.getDimension(R.styleable.CheckScoreView_out_layer_length, DEFAULT_OUT_LAYER_LENGTH);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = (int) ((mOutLayerLength + mRadius) * 2);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        drawOutLayer(canvas);
        drawInnerCircle(canvas);
        drawInnerText(canvas);
        canvas.restore();
    }

    private void drawOutLayer(Canvas canvas) {
        if (outLayerRadius <= mRadius + mOutLayerLength * 0.5f) {
            mPaint.setColor(Color.parseColor("#4C4315"));
            canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, outLayerRadius, mPaint);
        } else {
            mPaint.setColor(Color.parseColor("#332F16"));
            canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, outLayerRadius, mPaint);
            mPaint.setColor(Color.parseColor("#4C4315"));
            canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, mRadius + mOutLayerLength * 0.5f, mPaint);
        }
    }

    private void drawInnerCircle(Canvas canvas) {
        mPaint.setColor(Color.parseColor("#CDB319"));
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, mRadius, mPaint);
    }

    private void drawInnerText(Canvas canvas) {
        mPaint.getFontMetrics(mMetrics);
        float textSize = Utils.dp2px(32);
        mPaint.setColor(Color.parseColor("#ffffff"));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setTextSize(textSize);
        float baseLine = (getHeight() >> 1) - (mMetrics.ascent + mMetrics.descent) * 0.5f;
        canvas.drawText(mScore, getWidth() >> 1, baseLine, mPaint);
    }

    private void outLayerAnim() {
        if (mOutLayerAnim == null) {
            PropertyValuesHolder outLayerHOlder  = PropertyValuesHolder.ofFloat("outLayerRadius",
                    mRadius, mOutLayerLength + mRadius, mOutLayerLength + mRadius, mRadius);
            PropertyValuesHolder alpha = PropertyValuesHolder.ofFloat("alpha", 0.9f, 1f, 1f, 0.9f);
            mOutLayerAnim = ObjectAnimator.ofPropertyValuesHolder(this, outLayerHOlder, alpha);
            mOutLayerAnim.setInterpolator(new LinearInterpolator());
            mOutLayerAnim.setDuration(5000);
            mOutLayerAnim.setRepeatCount(-1);
        }
        mOutLayerAnim.start();
    }

    public void beat() {
        PropertyValuesHolder scaleX = PropertyValuesHolder.ofFloat("scaleX", 0.9f, 1f, 0.9f);
        PropertyValuesHolder scaleY = PropertyValuesHolder.ofFloat("scaleY", 0.9f, 1f, 0.9f);
        ObjectAnimator animator = ObjectAnimator.ofPropertyValuesHolder(this, scaleX, scaleY);
        animator.setDuration(5000);
        animator.setRepeatCount(-1);
        animator.start();
    }

    public void setScore(int score) {
        mScore = String.format(Locale.CHINA, "%d分", score);
        invalidate();
    }

    public void setOutLayerRadius(float outLayerRadius) {
        this.outLayerRadius = outLayerRadius;
        invalidate();
    }

}
