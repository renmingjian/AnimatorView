package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.text.style.WrapTogetherSpan;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

/**
 * Created by mj on 2020/7/13 21:09
 */
public class AirControlView extends View {

    private static final int MARGIN = (int) Utils.dp2px(6);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mRadius;
    private int mStrokeWidth;
    private float progress;
    private LinearGradient mGradient1;
    private LinearGradient mGradient2;
    private LinearGradient mGradient3;
    private LinearGradient mGradient4;

    public AirControlView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        startAnim();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        final int size = Math.min(width, height);
        setMeasuredDimension(resolveSize(size, widthMeasureSpec), resolveSize(size, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mRadius = mStrokeWidth = getWidth() / 6;
        mRadius = getWidth() >> 1;
        mRadius -= mStrokeWidth >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        drawOuterRing(canvas);
        drawInnerCircle(canvas);
        drawAngle(canvas);
        canvas.restore();
    }

    private void drawOuterRing(Canvas canvas) {
        mPaint.setStyle(Paint.Style.STROKE);
        RectF rectF = new RectF((-getWidth() + mStrokeWidth) >> 1, (-getHeight() + mStrokeWidth) >> 1, (getHeight() - mStrokeWidth) >> 1, (getHeight() - mStrokeWidth) >> 1);
        mPaint.setStrokeWidth(mStrokeWidth);
        double switchAngel = Math.PI / 180;
        mGradient1 = new LinearGradient((float) Math.cos(270 * switchAngel) * mRadius, (float) Math.sin(270 * switchAngel) * mRadius, (float) Math.cos(0 * switchAngel) * mRadius, (float) Math.sin(0 * switchAngel) * mRadius, Color.parseColor("#123456"), Color.parseColor("#789abc"), Shader.TileMode.CLAMP);
        mGradient2 = new LinearGradient((float) Math.cos(0 * switchAngel) * mRadius, (float) Math.sin(0 * switchAngel) * mRadius, (float) Math.cos(90 * switchAngel) * mRadius, (float) Math.sin(90 * switchAngel) * mRadius, Color.parseColor("#789abc"), Color.parseColor("#def123"), Shader.TileMode.CLAMP);
        mGradient3 = new LinearGradient((float) Math.cos(90 * switchAngel) * mRadius, (float) Math.sin(90 * switchAngel) * mRadius, (float) Math.cos(180 * switchAngel) * mRadius, (float) Math.sin(180 * switchAngel) * mRadius, Color.parseColor("#def123"), Color.parseColor("#789abc"), Shader.TileMode.CLAMP);
        mGradient4 = new LinearGradient((float) Math.cos(180 * switchAngel) * mRadius, (float) Math.sin(180 * switchAngel) * mRadius, (float) Math.cos(270 * switchAngel) * mRadius, (float) Math.sin(270 * switchAngel) * mRadius, Color.parseColor("#789abc"), Color.parseColor("#123456"), Shader.TileMode.CLAMP);
        mPaint.setShader(mGradient1);
        canvas.drawArc(rectF, 270, 90, false, mPaint);
        mPaint.setShader(mGradient2);
        canvas.drawArc(rectF, 0, 90, false, mPaint);
        mPaint.setShader(mGradient3);
        canvas.drawArc(rectF, 90, 90, false, mPaint);
        mPaint.setShader(mGradient4);
        canvas.drawArc(rectF, 180, 90, false, mPaint);
    }

    private void drawInnerCircle(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(Color.parseColor("#123456"));
        canvas.drawCircle(0, 0, mRadius - (mStrokeWidth >> 1) - MARGIN, mPaint);
    }

    private void drawAngle(Canvas canvas) {
        double angle = 270 + 360 * progress;
        double topAngle = angle * Math.PI / 180;
        double leftAngle = (angle - 15) * Math.PI / 180;
        double rightAngle = (angle + 15) * Math.PI / 180;
        double innerCircleRadius = mRadius - (mStrokeWidth >> 1) - MARGIN;
        double topAngleRadius = mRadius;
        int topX = (int) (Math.cos(topAngle) * topAngleRadius);
        int topY = (int) (Math.sin(topAngle) * topAngleRadius);
        int leftX = (int) (Math.cos(leftAngle) * innerCircleRadius);
        int leftY = (int) (Math.sin(leftAngle) * innerCircleRadius);
        int rightX = (int) (Math.cos(rightAngle) * innerCircleRadius);
        int rightY = (int) (Math.sin(rightAngle) * innerCircleRadius);
        Path path = new Path();
        path.moveTo(topX, topY);
        path.lineTo(leftX,  leftY);
        path.lineTo(rightX,  rightY);
        path.close();
        canvas.drawPath(path, mPaint);
    }

    private void startAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0f, 1f);
        animator.setDuration(3000);
        animator.start();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
