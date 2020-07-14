package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.view.ViewCompat;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by erge 2020-03-26 09:55
 */
public class VerticalScrollView extends FrameLayout {

    private View mTopLayout;
    private View mBottomLayout;
    private int mTopLayoutHeight;
    private ViewDragHelper mDragViewHelper;
    private OnScrollListener mOnScrollListener;
    private GestureDetector gestureDetector;

    public VerticalScrollView(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDragViewHelper = ViewDragHelper.create(this, callback);
        gestureDetector = new GestureDetector(new GestureDetector.OnGestureListener() {
            @Override
            public boolean onDown(MotionEvent e) {
                return false;
            }

            @Override
            public void onShowPress(MotionEvent e) {

            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                return false;
            }

            @Override
            public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
                return false;
            }

            @Override
            public void onLongPress(MotionEvent e) {

            }

            @Override
            public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
                return false;
            }
        });
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() > 2) {
            throw new IllegalArgumentException("VerticalScrollView contains only two children");
        }
        mTopLayout = getChildAt(0);
        mBottomLayout = getChildAt(1);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mTopLayoutHeight = mTopLayout.getMeasuredHeight();
    }

    public void setMaxDragRange(int maxDragRange) {
        mTopLayoutHeight = maxDragRange;
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mTopLayout.layout(0, 0, getWidth(), mTopLayoutHeight);
        mBottomLayout.layout(0, mTopLayoutHeight, getMeasuredWidth(), mTopLayoutHeight + mBottomLayout.getMeasuredHeight());
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragViewHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragViewHelper.processTouchEvent(event);
        return true;
    }

    private final ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {

        @Override
        public boolean tryCaptureView(View child, int pointerId) {
            return mBottomLayout == child || mTopLayout == child;
        }

        @Override
        public int clampViewPositionHorizontal(View child, int left, int dx) {
            return 0;
        }

        @Override
        public int getViewVerticalDragRange(View child) {
            return mTopLayoutHeight;
        }

        /**
         * 限制mainView在垂直方向上滑动的距离
         */
        @Override
        public int clampViewPositionVertical(View child, int top, int dy) {
            // 计算mainView的顶部将要变成的坐标值：当前的顶部坐标+滑动的距离
            int newTop;
            if (child == mBottomLayout) {
                newTop = mBottomLayout.getTop() + dy;
                if (newTop > mTopLayoutHeight) newTop = mTopLayoutHeight;
                if (newTop < 0) newTop = 0;
            } else {
                newTop = mTopLayout.getTop() + dy;
                if (newTop < -mTopLayoutHeight) newTop = -mTopLayoutHeight;
                if (newTop > 0) newTop = 0;
            }

            return newTop;
        }

        @Override
        public void onViewPositionChanged(View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            System.out.println("top = " + top + "--dx = " + dy);
            if (changedView == mBottomLayout) { // 滑动的是底部View
                mBottomLayout.layout(0, top, mBottomLayout.getMeasuredWidth(),
                        top + mBottomLayout.getMeasuredHeight());
                // 让底部的View滑动距离是底部滑动距离的一半
                mTopLayout.layout(0, (top - mTopLayoutHeight) / 2, mTopLayout.getWidth(),
                        (top - mTopLayoutHeight) / 2 + mTopLayout.getMeasuredHeight());

                // 设置回调
                final float alpha = (float) top / mTopLayoutHeight;
                mTopLayout.setAlpha(alpha);
                if (mOnScrollListener != null)
                    mOnScrollListener.onScroll(top, mTopLayoutHeight, 1 - alpha, dy == 0);

            } else { // 滑动的是顶部View
                // 先把顶部View摆放好

                mTopLayout.layout(0, top, mTopLayout.getMeasuredWidth(), top + mTopLayout.getMeasuredHeight());
                // 声明底部View的真正top，当滑动顶部View时让底部的滑动距离是顶部的一半
                int realTop = (int) (top * 1.5f + mTopLayout.getMeasuredHeight());
                if (realTop < 0) realTop = 0;
                if (realTop > mTopLayoutHeight) realTop = mTopLayoutHeight;
                mBottomLayout.layout(0,
                        realTop,
                        mBottomLayout.getWidth(),
                        realTop + mBottomLayout.getMeasuredHeight());
                float alpha = realTop * 1f / mTopLayoutHeight;
                mTopLayout.setAlpha(alpha);
                if (mOnScrollListener != null)
                    mOnScrollListener.onScroll(mTopLayoutHeight - realTop, mTopLayoutHeight,
                            1 - alpha, dy == 0);
            }
        }

        /**
         * 当手指抬起时执行这个方法
         */
        @Override
        public void onViewReleased(View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);
            whenFingerUp();
        }

    };

    /**
     * 当手抬起时，判断是要close，还是要open
     */
    private void whenFingerUp() {
        if (mBottomLayout.getTop() < mTopLayoutHeight / 2) {
            close();
        } else {
            open();
        }
    }

    /**
     * 完全遮盖上面部分:
     * 调用了smoothSlideViewTo方法之后，onViewPositionChanged方法也会走，在该方法中同时对两个View进行滑动
     */
    public void close() {
        mDragViewHelper.smoothSlideViewTo(mBottomLayout, 0, 0);
        ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
    }

    /**
     * 完全打开上面部分
     */
    public void open() {
        mDragViewHelper.smoothSlideViewTo(mBottomLayout, 0, mTopLayoutHeight);
        ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
    }

    /**
     * invalide是为了引起onDraw回调，onDraw又调用computeScroll；
     * 所以调用invalidate()是为了调用computeScroll()
     */
    @Override
    public void computeScroll() {
        // 若滚动动画没有结束，即位置发生改变，则刷新新的位置
        if (mDragViewHelper.continueSettling(true)) {
            ViewCompat.postInvalidateOnAnimation(VerticalScrollView.this);
        }
    }

    /**
     * Main布局垂直滑动距离的监听
     */
    public interface OnScrollListener {
        /**
         * @param instance    滑动的距离
         * @param maxInstance 最大的滑动距离
         * @param alpha       滑动的距离与上面布局高度的比值
         * @param up          是否是向上滑动
         */
        void onScroll(int instance, int maxInstance, float alpha, boolean up);
    }

    public void setOnScrollListener(OnScrollListener onScrollListener) {
        this.mOnScrollListener = onScrollListener;
    }

}
