package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.OverScroller;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.List;

/**
 * 柱状图
 * Created by erge 2019-09-12 09:53
 */
public class HistogramView extends View implements Runnable {

    private Paint mPaint;
    private GestureDetector mGestureDetector;
    private OverScroller mScroller;
    // 数据源
    private List<HistogramInfo> mData;
    // 整个柱状图的宽度（非View的宽度）
    private int mTotalWidth;
    // 整个View的高度
    private int mHeight;
    // 整个View的宽度
    private int mWidth;
    // 底部线条高度
    private int mLineHeight = (int) Utils.dp2px(1);
    // 柱状图上方数量文字大小
    private int mCountTextSize = (int) Utils.dp2px(14);
    // 时间段文字大小
    private int mTimeTextSize = (int) Utils.dp2px(14);
    // 柱状图距离柱状图上方文字的艰巨
    private int mCountTextMargin = (int) Utils.dp2px(5);
    // 每个时间段文字之间的距离
    private int mTimeTextMargin = (int) Utils.dp2px(10);
    // 柱状图最大的高度
    private int mHistogramHeight;
    // 每个时间段描述文字的宽度
    private float mTimeTextWidth;
    // 柱状图最大的数量
    private int mMaxCount = 1;
    private Paint.FontMetrics metrics = new Paint.FontMetrics();
    // 左右滑动柱状图时需要平移的距离
    private float mOffset;
    // 距离屏幕左侧距离
    private float mLeftMargin = Utils.dp2px(15);
    // 距离屏幕右侧距离
    private float mRightMargin = Utils.dp2px(15);
    private float mDownX, mDownY;

    {
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
        mPaint.setDither(true);
    }

    public HistogramView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mScroller = new OverScroller(context);
        mGestureDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                return true;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                mOffset -= distanceX;
                // 限制左右滑动距离
                if (mOffset > 0) {
                    mOffset = 0;
                }
                if (mOffset <= -(mTotalWidth - mWidth)) {
                    mOffset = -mTotalWidth + mWidth;
                }
                invalidate();
                return false;
            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                mScroller.fling((int) mOffset, 0, (int) velocityX, (int) velocityY,
                        -(mTotalWidth - mWidth), 0, 0, 0);
                postOnAnimation(HistogramView.this);
                return false;
            }
        });
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handled = true;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                mDownX = event.getX();
                mDownY = event.getY();
                break;
            case MotionEvent.ACTION_MOVE:
                float moveX = event.getX();
                float moveY = event.getY();
                if (Math.abs(moveX - mDownX) < Math.abs(moveY - mDownY)) {
                    handled = false;
                } else {
                    getParent().requestDisallowInterceptTouchEvent(true);
                }
                break;
        }
        return handled && mGestureDetector.onTouchEvent(event);
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
        mHistogramHeight = mHeight - mLineHeight - mCountTextSize - mCountTextMargin - mTimeTextSize - mTimeTextMargin;
        mPaint.setTextSize(mTimeTextSize);
        mTimeTextWidth = mPaint.measureText("07-08");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (mData == null || mData.size() == 0) return;
        canvas.save();
        canvas.translate(mOffset, 0);
        mPaint.setStrokeWidth(mLineHeight);
        mPaint.setColor(0xff8c8c8c);
        mPaint.getFontMetrics(metrics);
        float lineY = mHeight - mTimeTextSize - mTimeTextMargin;
        // 底部的一条横向
        canvas.drawLine(0, lineY, mTotalWidth, lineY, mPaint);
        for (int i = 0; i < mData.size(); i++) {
            HistogramInfo info = mData.get(i);
            float middle = mTimeTextWidth / 2 + (Utils.dp2px(20) + mTimeTextWidth) * i + mLeftMargin;
            float timeTextStart = (mTimeTextWidth + Utils.dp2px(20)) * i + mLeftMargin;

            // 画底部时间文字
            mPaint.setColor(0xff8c8c8c);
            mPaint.setTextSize(mTimeTextSize);
            mPaint.setTextAlign(Paint.Align.LEFT);
            canvas.drawText(info.getTime(), timeTextStart, mHeight, mPaint);

            // 画柱状图
            mPaint.setStrokeWidth(Utils.dp2px(18));
            mPaint.setColor(0xffD7BE1E);
            float histogramHeight = mHistogramHeight * (float) info.getCount() / mMaxCount;
            canvas.drawLine(middle, lineY, middle, lineY - mLineHeight - histogramHeight, mPaint);

            // 画柱状图上方文字
            mPaint.setColor(0xffD7BE1E);
            mPaint.setTextAlign(Paint.Align.CENTER);
            float countTextBaseLine = lineY - histogramHeight - mCountTextMargin;
            canvas.drawText(info.getCount() + "", middle, countTextBaseLine, mPaint);
        }
        canvas.restore();
    }

    public void setData(List<HistogramInfo> data) {
        this.mData = data;
        mTotalWidth = (int) ((mTimeTextWidth + Utils.dp2px(20)) * mData.size() + mRightMargin);
        for (int i = 0; i < mData.size(); i++) {
            if (mData.get(i).getCount() > mMaxCount) {
                mMaxCount = mData.get(i).getCount();
            }
        }
        invalidate();
    }

    @Override
    public void run() {
        if (mScroller.computeScrollOffset()) {
            mOffset = mScroller.getCurrX();
            invalidate();
            postOnAnimation(this);
        }
    }

    public static class HistogramInfo {

        private int count;
        private String time;

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getTime() {
            return time;
        }

        public void setTime(String time) {
            this.time = time;
        }
    }
}
