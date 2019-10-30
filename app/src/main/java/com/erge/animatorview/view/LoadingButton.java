package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;


/**
 * Created by erge 2019-08-28 17:21
 */
public class LoadingButton extends View {

    private static final int PAINT_STROKE_WIDTH = 4;

    private State mState = State.NORMAL;
    private int mWidth;
    private int mHeight;
    private float mRadius;
    private float dotRadius;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float lineProgress = 1f;
    private float circleProgress = 0f;
    private GestureDetector mDetector;
    private String mText;
    private OnLoadingListener mOnLoadingListener;
    private OnClickListener mOnClickListener;


    public enum State {
        // 正常的Button状态
        NORMAL,
        // Loading状态
        LOADING,
        // Loading完成状态
        COMPLETE,
        // Loaing错误状态
        ERROR
    }


    public LoadingButton(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(context, attrs);
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                switch (mState) {
                    case NORMAL:
                        if (mOnClickListener != null) {
                            mOnClickListener.onNormalClick();
                        }
                        startGradientAnim();
                        break;
                    case LOADING:
                        if (mOnLoadingListener != null) {
                            mOnLoadingListener.onStartLoading();
                        }
                        if (mOnClickListener != null) {
                            mOnClickListener.onLoadingClick();
                        }
                        break;
                    case COMPLETE:
                        if (mOnClickListener != null) {
                            mOnClickListener.onCompleteClick();
                        }
                        break;
                    case ERROR:
                        if (mOnClickListener != null) {
                            mOnClickListener.onErrorClick();
                        }
                        break;
                }
                return super.onSingleTapUp(e);
            }
        });
    }

    private void initAttrs(Context context, AttributeSet attrs) {
        final TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.LoadingButton);
        mText = typedArray.getString(R.styleable.LoadingButton_text);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mDetector.onTouchEvent(event);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mHeight >> 1;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        switch (mState) {
            case NORMAL:
                drawButton(canvas);
                break;
            case LOADING:
                drawLoading(canvas);
                break;
            case COMPLETE:
                drawComplete(canvas);
                break;
            case ERROR:
                drawError(canvas);
                break;
        }
    }

    private void drawButton(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#ffd700"));
        mPaint.setStyle(Paint.Style.FILL);
        float width = mWidth - 2 * mRadius;
        // 左侧半圆
        canvas.drawArc(width * (1f - lineProgress) / 2, 0, width * (1f - lineProgress) / 2 + mRadius * 2,
                mHeight, 90, 180, false, mPaint);
        // 中间矩形
        canvas.drawRect(width * (1f - lineProgress) / 2 + mRadius, 0,
                width * lineProgress / 2f + mWidth / 2f, mHeight, mPaint);
        // 右侧半圆
        canvas.drawArc(mWidth / 2f + width * lineProgress / 2f - mRadius, 0,
                mWidth / 2f + width * lineProgress / 2f + mRadius,
                mHeight, 270, 180, false, mPaint);
        // 画文字
        if (!TextUtils.isEmpty(mText)) {
            mPaint.setTextSize(mHeight / 2f * lineProgress);
            mPaint.setColor(Color.parseColor("#ffffff"));
            mPaint.setTextAlign(Paint.Align.CENTER);
            Paint.FontMetrics metrics = new Paint.FontMetrics();
            mPaint.getFontMetrics(metrics);
            float offset = (metrics.ascent + metrics.descent) / 2;
            float baseLine = (mHeight >> 1) - offset;
            canvas.drawText(mText, mWidth >> 1, baseLine, mPaint);
        }
        canvas.restore();
    }

    private void drawLoading(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#ffd700"));
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc((mWidth >> 1) - mRadius, PAINT_STROKE_WIDTH >> 1, (mWidth >> 1) + mRadius,
                mHeight - (PAINT_STROKE_WIDTH >> 1), 270,
                circleProgress * 360, false, mPaint);
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(mWidth >> 1, mHeight >> 1, dotRadius, mPaint);
        canvas.restore();
    }

    private void drawComplete(Canvas canvas) {
        canvas.save();
        mPaint.setColor(Color.parseColor("#ffd700"));
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth >> 1, mHeight >> 1, mRadius - (PAINT_STROKE_WIDTH >> 1), mPaint);
        canvas.drawLine((mWidth - mRadius) / 2, (mHeight >> 1) - mRadius / 4, mWidth >> 1,
                (mHeight >> 1) + mRadius / 4, mPaint);
        canvas.drawLine((mWidth >> 1), (mHeight >> 1) + mRadius / 4, (mWidth + mRadius) / 2,
                (mHeight - mRadius) / 2, mPaint);
        canvas.restore();
    }

    private void drawError(Canvas canvas) {
        canvas.save();
        mPaint.setStrokeWidth(PAINT_STROKE_WIDTH);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawCircle(mWidth >> 1, mHeight >> 1, mRadius - (PAINT_STROKE_WIDTH >> 1), mPaint);
        canvas.drawLine((mWidth - mRadius) / 2, (mHeight - mRadius) / 2, (mWidth + mRadius) / 2,
                (mHeight + mRadius) / 2, mPaint);
        canvas.drawLine((mWidth - mRadius) / 2, (mHeight + mRadius) / 2, (mWidth + mRadius) / 2,
                (mHeight - mRadius) / 2, mPaint);
        canvas.restore();
    }

    /**
     * 开启渐变为圆的效果
     */
    private void startGradientAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "lineProgress", 1f, 0f);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value == 0f) {
                    postDelayed(new Runnable() {
                        @Override
                        public void run() {
                            setState(State.LOADING);
                        }
                    }, 300);
                }
            }
        });
        animator.start();
    }

    /**
     * 圆内圆点动画
     */
    private void startDotAnim() {
        ObjectAnimator animatorDot = ObjectAnimator.ofFloat(this, "dotRadius", mRadius / 2, mRadius / 3 * 2, mRadius / 2);
        animatorDot.setDuration(800);
        animatorDot.setRepeatCount(-1);
        animatorDot.start();

        ObjectAnimator animatorCircle = ObjectAnimator.ofFloat(this, "circleProgress", 0f, 1f);
        animatorCircle.setDuration(1000);
        animatorCircle.setRepeatCount(-1);
        animatorCircle.start();
    }

    public State getState() {
        return mState;
    }

    public void setState(State state) {
        this.mState = state;
        if (state == State.LOADING) {
            startDotAnim();
        } else if (state == State.NORMAL) {
            lineProgress = 1f;
            circleProgress = 0;
        } else if (state == State.COMPLETE) {

        }
        invalidate();
    }

    public float getLineProgress() {
        return lineProgress;
    }

    public void setLineProgress(float lineProgress) {
        this.lineProgress = lineProgress;
        invalidate();
    }

    public float getDotRadius() {
        return dotRadius;
    }

    public void setDotRadius(float dotRadius) {
        this.dotRadius = dotRadius;
        invalidate();
    }

    public float getCircleProgress() {
        return circleProgress;
    }

    public void setCircleProgress(float circleProgress) {
        this.circleProgress = circleProgress;
        invalidate();
    }

    public void setOnLoadingListener(OnLoadingListener onLoadingListener) {
        this.mOnLoadingListener = onLoadingListener;
    }

    public void setOnClickListener(OnClickListener onClickListener) {
        this.mOnClickListener = onClickListener;
    }

    /**
     * Loading监听
     */
    public interface OnLoadingListener {
        void onStartLoading();
    }

    /**
     * 点击事件监听
     */
    public interface OnClickListener {
        // 正常状态下的点击事件
        void onNormalClick();

        // Loading状态下的点击事件
        void onLoadingClick();

        // 加载完成后的点击事件
        void onCompleteClick();

        // 加载错误情况下的点击事件
        void onErrorClick();
    }

}
