package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;


/**
 * 仪表盘
 * Created by erge 2019-10-24 15:46
 */
public class BoardView extends View {

    private static final int LEFT_START_ANGLE = 120;
    private static final int SWIPE_ANGLE = 150;
    private static final int RIGHT_START_ANGLE = 270;
    private static final int GRADIENT_GRAY_COLOR = 0x33ffffff;
    private static final int GRADIENT_DARK_COLOR = 0xccffffff;
    private static final float BIG_RING_WIDTH = Utils.dp2px(8);
    private static final float SMALL_RING_WIDTH = Utils.dp2px(4);
    private static final float DOT_RADIUS = Utils.dp2px(5);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private RectF mBigCircle;
    private RectF mSmallCircle;
    // 大圆半径
    private float mBigRadius;
    // 小圆半径
    private float mSmallRadius;
    private Paint.FontMetrics mMetrics;
    // 小圆点位置集合
    private RectF[] mDotsLocation = new RectF[31];
    private String[] mTexts = {"0", "10", "20", "30", "40", "50", "60", "70", "80", "90", "100"};
    // 指针旋转角度
    private float angle = 120;
    // 中心数量文字
    private int count;

    public BoardView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setDither(true);
        mMetrics = new Paint.FontMetrics();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mBigRadius = getHeight() * 0.5f;
        mSmallRadius = mBigRadius * 0.35f;
        mBigCircle = new RectF(-mBigRadius + BIG_RING_WIDTH * 0.5f, -mBigRadius + BIG_RING_WIDTH * 0.5f,
                mBigRadius - BIG_RING_WIDTH * 0.5f, mBigRadius - BIG_RING_WIDTH * 0.5f);
        mSmallCircle = new RectF(-mSmallRadius, -mSmallRadius, mSmallRadius, mSmallRadius);
        for (int i = 0; i < 31; i++) {
            float x = (float) Math.cos(Math.PI / 3d * 2 + i * Math.PI / 18d) * mBigRadius * 0.8f;
            float y = (float) Math.sin(Math.PI / 3d * 2 + i * Math.PI / 18d) * mBigRadius * 0.8f;
            RectF rectF = new RectF(x, y, 0, 0);
            mDotsLocation[i] = rectF;
        }
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        drawBigRing(canvas);
        drawSmallRing(canvas);
        drawInnerText(canvas);
        drawDots(canvas);
        drawPointer(canvas);
        drawMarkText(canvas);
        canvas.restore();
    }

    /**
     * 外侧大圆
     */
    private void drawBigRing(Canvas canvas) {
        float startX = (float) Math.cos(Math.PI / 3);
        float startY = (float) Math.sin(Math.PI / 3);
        LinearGradient gradient = new LinearGradient(startX, startY, 0, -mBigRadius, GRADIENT_GRAY_COLOR, GRADIENT_DARK_COLOR,
                Shader.TileMode.CLAMP);
        mPaint.setStrokeWidth(BIG_RING_WIDTH);
        mPaint.setColor(GRADIENT_DARK_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(gradient);
        canvas.drawArc(mBigCircle, LEFT_START_ANGLE, SWIPE_ANGLE, false, mPaint);
        canvas.drawArc(mBigCircle, RIGHT_START_ANGLE, SWIPE_ANGLE, false, mPaint);
    }

    /**
     * 内侧小圆
     */
    private void drawSmallRing(Canvas canvas) {
        mPaint.setStrokeWidth(SMALL_RING_WIDTH);
        mPaint.setColor(GRADIENT_GRAY_COLOR);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setShader(null);
        canvas.drawArc(mSmallCircle, LEFT_START_ANGLE, SWIPE_ANGLE, false, mPaint);
        canvas.drawArc(mSmallCircle, RIGHT_START_ANGLE, SWIPE_ANGLE, false, mPaint);
    }

    /**
     * 中心文字
     */
    private void drawInnerText(Canvas canvas) {
        mPaint.setColor(0xddffffff);
        mPaint.setTextSize(Utils.dp2px(48));
        mPaint.getFontMetrics(mMetrics);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setShader(null);
        float baseLine = -(mMetrics.ascent + mMetrics.descent) * 0.5f;
        canvas.drawText("" + count, 0, baseLine, mPaint);
    }

    /**
     * 大圆内侧小圆点
     */
    private void drawDots(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);

        for (int i = 0; i < mDotsLocation.length; i++) {
            RectF rectF = mDotsLocation[i];
            if (i % 3 == 0) {
                mPaint.setColor(0xddffffff);
                mPaint.setMaskFilter(null);
            } else {
                mPaint.setColor(0x55ffffff);
                mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(5), BlurMaskFilter.Blur.NORMAL));
            }
            canvas.drawCircle(rectF.left, rectF.top, DOT_RADIUS, mPaint);
        }
    }

    /**
     * 画指针
     */
    private void drawPointer(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffd7be1e);
        mPaint.setStrokeWidth(Utils.dp2px(4));
        float startX = (float) Math.cos(angle * Math.PI / 180) * mSmallRadius;
        float startY = (float) Math.sin(angle * Math.PI / 180) * mSmallRadius;
        float endX = (float) Math.cos(angle * Math.PI / 180) * mBigRadius * 0.7f;
        float endY = (float) Math.sin(angle * Math.PI / 180) * mBigRadius * 0.7f;
        canvas.drawCircle(startX, startY, Utils.dp2px(4), mPaint);
        canvas.drawLine(startX * 1.2f, startY * 1.2f, endX, endY, mPaint);
    }

    /**
     * 画刻度值
     */
    private void drawMarkText(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xddffffff);
        mPaint.setTextSize(Utils.dp2px(14));
        mPaint.setTextAlign(Paint.Align.LEFT);

        // 添加一条虚拟的Path圆弧，根据圆弧位置画文字
        Path path = new Path();
        mPaint.setTextSize(Utils.dp2px(14));
        RectF rectF = new RectF(-mBigRadius * 0.6f, -mBigRadius * 0.6f,
                mBigRadius * 0.6f, mBigRadius * 0.6f);
        path.addArc(rectF, 120f, 300f);

        for (int i = 0; i < mDotsLocation.length; i++) {
            if (i % 3 == 0) {
                // 设置文字开始的偏移量，需要根据角度算出弧度
                final float startOffset = i == mDotsLocation.length - 1 ? Utils.dp2px(20) : Utils.dp2px(9);
                canvas.drawTextOnPath(mTexts[i / 3], path,
                        (float) (2 * Math.PI * mBigRadius * 0.6f * i * 10 / 360 - startOffset), 0, mPaint);
            }
        }
    }

    /**
     * 指针动画
     */
    public void pointerAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "angle", 120f, 410f, 400f);
        animator.setDuration(2500);
        animator.start();
        countAnim();
    }

    /**
     * 文字数量动画
     */
    private void countAnim() {
        ObjectAnimator animator = ObjectAnimator.ofInt(this, "count", 0, 93);
        animator.setDuration(2000);
        animator.start();
    }

    @SuppressWarnings("unused")
    public void setAngle(float angle) {
        this.angle = angle;
        invalidate();
    }

    public void setCount(int count) {
        this.count = count;
    }

    @SuppressWarnings("unused")
    public void setCountText(int count) {
        this.count = count;
        pointerAnim();
    }
}
