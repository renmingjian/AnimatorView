package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;


import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

import java.util.Locale;

/**
 * Created by erge 2019-10-28 15:14
 */
public class BoardView2 extends View {

    private static final float BIG_CIRCLE_WIDTH = Utils.dp2px(6);
    private static final float BIG_CIRCLE_INNER_GRADIENT_WIDTH = Utils.dp2px(6);
    private static final float BIG_CIRCLE_OUTER_GRADIENT_WIDTH = Utils.dp2px(3);
    private static final float BIG_RADIUS = Utils.dp2px(110);
    private static final float SMALL_RADIUS = Utils.dp2px(45);
    private static final float SMALL_CIRCLE_WIDTH = Utils.dp2px(2);
    private static final String[] TEXTS = {"9", "10", "", "0", "1", "2", "3", "4", "5", "6", "7", "8"};

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 刻度表上的刻度线起始位置
    private RectF[] mMarkLocation = new RectF[48];
    private int score = 0;
    private float progress = 0f;


    public BoardView2(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = (int) Utils.dp2px(300);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        for (int i = 0; i < mMarkLocation.length; i++) {
            RectF rectF = new RectF();
            rectF.left = (float) (Math.cos(i * 7.5f * Math.PI / 180f) * BIG_RADIUS);
            rectF.top = (float) (Math.sin(i * 7.5f * Math.PI / 180f) * BIG_RADIUS);
            float f = i % 4 == 0 ? Utils.dp2px(20) : Utils.dp2px(10);
            rectF.right = (float) (Math.cos(i * 7.5f * Math.PI / 180f) * (BIG_RADIUS + f));
            rectF.bottom = (float) (Math.sin(i * 7.5f * Math.PI / 180f) * (BIG_RADIUS + f));
            mMarkLocation[i] = rectF;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        drawBigRing(canvas);
        drawSmallRing(canvas);
        drawMark(canvas);
        drawMarkPointer(canvas);
        drawInnerText(canvas);
        drawPointer(canvas);
        canvas.restore();
    }

    private void drawBigRing(Canvas canvas) {

        // 内侧渐变
        mPaint.setShader(null);
        mPaint.setColor(0xff247DC5);
        mPaint.setMaskFilter(new BlurMaskFilter(BIG_CIRCLE_INNER_GRADIENT_WIDTH, BlurMaskFilter.Blur.NORMAL));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BIG_CIRCLE_INNER_GRADIENT_WIDTH);
        canvas.drawCircle(0, 0, BIG_RADIUS - BIG_CIRCLE_INNER_GRADIENT_WIDTH, mPaint);

        // 外侧渐变
        mPaint.setShader(null);
        mPaint.setColor(0xff247DC5);
        mPaint.setMaskFilter(new BlurMaskFilter(BIG_CIRCLE_INNER_GRADIENT_WIDTH, BlurMaskFilter.Blur.NORMAL));
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BIG_CIRCLE_OUTER_GRADIENT_WIDTH);
        canvas.drawCircle(0, 0, BIG_RADIUS + BIG_CIRCLE_INNER_GRADIENT_WIDTH, mPaint);

        mPaint.setShader(null);
        mPaint.setColor(0xdd000000);
        mPaint.setMaskFilter(null);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(1));
        canvas.drawCircle(0, 0, BIG_RADIUS, mPaint);

        mPaint.setShader(null);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(BIG_CIRCLE_WIDTH);
        mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(2), BlurMaskFilter.Blur.SOLID));
        canvas.drawCircle(0, 0, BIG_RADIUS, mPaint);
    }

    private void drawSmallRing(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(0xff247DC5);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(SMALL_CIRCLE_WIDTH);
        canvas.drawCircle(0, 0, SMALL_RADIUS, mPaint);
    }

    private void drawMark(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(1), BlurMaskFilter.Blur.NORMAL));
        for (int i = 0; i < mMarkLocation.length; i++) {
            RectF rectF = mMarkLocation[i];
            canvas.drawLine(rectF.left, rectF.top, rectF.right, rectF.bottom, mPaint);
        }
    }

    private void drawMarkPointer(Canvas canvas) {
        Paint.FontMetrics metrics = new Paint.FontMetrics();
        mPaint.setShader(null);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(1));
        mPaint.setMaskFilter(null);
        mPaint.setTextSize(Utils.dp2px(14));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getFontMetrics(metrics);
        for (int i = 0; i < mMarkLocation.length; i++) {
            if (i % 4 == 0) {
                RectF rectF = mMarkLocation[i];
                float baseLine = rectF.top * 0.8f - (metrics.ascent + metrics.descent) / 2;
                canvas.drawText(TEXTS[i / 4], rectF.left * 0.8f, baseLine, mPaint);
            }
        }
    }

    private void drawInnerText(Canvas canvas) {
        Paint.FontMetrics metrics = new Paint.FontMetrics();
        mPaint.setShader(null);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        mPaint.setMaskFilter(null);
        mPaint.setTextSize(Utils.dp2px(32));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.getFontMetrics(metrics);
        canvas.drawText(String.format(Locale.CHINA, "%d", score), 0, -(metrics.ascent + metrics.descent) / 2, mPaint);
    }

    private void drawPointer(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(0xffffffff);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(1), BlurMaskFilter.Blur.NORMAL));
        float startX = (float) Math.cos(90d * Math.PI / 180d + progress * 360d / 120d * Math.PI / 180d) * SMALL_RADIUS;
        float startY = (float) Math.sin(90d * Math.PI / 180d + progress * 360d / 120d * Math.PI / 180d) * SMALL_RADIUS;
        float endX = (float) Math.cos(90d * Math.PI / 180d + progress * 360d / 120d * Math.PI / 180d) * BIG_RADIUS * 0.7f;
        float endY = (float) Math.sin(90d * Math.PI / 180d + progress * 360d / 120d * Math.PI / 180d) * BIG_RADIUS * 0.7f;
        canvas.drawCircle(startX, startY, Utils.dp2px(3), mPaint);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        mPaint.setMaskFilter(null);
        canvas.drawLine(startX * 1.1f, startY * 1.1f, endX, endY, mPaint);
    }


    public void setScore(int score) {
        this.score = score;
    }

    public void setProgress(float progress) {
        this.progress = progress;
        this.score = (int) progress;
        invalidate();
    }

    public void startAnim(float score) {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0f, score + 3f, score);
        animator.setDuration(2000);
        animator.start();
    }

}
