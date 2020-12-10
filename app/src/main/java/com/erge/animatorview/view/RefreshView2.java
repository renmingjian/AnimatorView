package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by erge 2020/11/4 3:55 PM
 */
public class RefreshView2 extends FrameLayout {

    // 刷新头部
    private RefreshHeaderView mRefreshHeaderView;
    // 中间内容区域
    private View mMiddleView;
    // 底部内容区域
    private View mBottomView;

    private ViewDragHelper mDragHelper;
    private RefreshView.State mState = RefreshView.State.NORMAL;

    public RefreshView2(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

        mDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        int mChildCount = getChildCount();
        if (mChildCount == 0) return;
        View view = getChildAt(0);
        if (view instanceof RefreshHeaderView) {
            mRefreshHeaderView = (RefreshHeaderView) view;
        } else {
            mRefreshHeaderView = new DefaultRefreshHeaderView(getContext());
            addView(mRefreshHeaderView, 0);
        }
        if (mChildCount == 2) {
            mMiddleView = getChildAt(1);
        }
        if (mChildCount == 2) {
            mBottomView = getChildAt(2);
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mRefreshHeaderView.layout(0, -mRefreshHeaderView.getMeasuredHeight(), getRight(), 0);
        if (mMiddleView != null) {
            mMiddleView.layout(0, 0, getRight(), mMiddleView.getMeasuredHeight());
        }
        if (mBottomView != null) {
            mBottomView.layout(0, mMiddleView.getHeight(), getRight(), mBottomView.getMeasuredHeight());
        }
    }

    public void setRefreshHeaderView(RefreshHeaderView refreshHeaderView) {
        this.mRefreshHeaderView = refreshHeaderView;
    }

    public void setState(RefreshView.State state) {
        this.mState = state;
    }

    @Override
    public boolean onInterceptTouchEvent(MotionEvent ev) {
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        mDragHelper.processTouchEvent(event);
        return true;
    }

    ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return true;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            System.out.println("getViewVerticalDragRange = ");
            return mRefreshHeaderView.getMeasuredHeight() + mMiddleView.getMeasuredHeight();
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            System.out.println("clampViewPositionVertical");
            // 计算mainView的顶部将要变成的坐标值：当前的顶部坐标+滑动的距离
            int newTop = 0;
            if (child == mMiddleView) {
                newTop = mMiddleView.getTop();
                if (newTop > 0) newTop = 0;
                if (newTop < -mMiddleView.getHeight()) newTop = -mMiddleView.getHeight();
            }
            if (child == mBottomView) {
                newTop = mBottomView.getTop();
                if (newTop > mMiddleView.getHeight()) newTop = mMiddleView.getHeight();
                if (newTop < 0) newTop = 0;
            }
            return newTop;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            super.onViewPositionChanged(changedView, left, top, dx, dy);
            int newTop = top;
            if (changedView == mMiddleView) {
                mRefreshHeaderView.layout(0, newTop - mRefreshHeaderView.getMeasuredHeight(), mRefreshHeaderView.getRight(), newTop);
                mMiddleView.layout(0, newTop, mMiddleView.getRight(), mMiddleView.getMeasuredHeight());
                mBottomView.layout(0, mMiddleView.getMeasuredHeight(), mBottomView.getRight(), mBottomView.getMeasuredHeight());
            }

            if (changedView == mBottomView) {
                mRefreshHeaderView.layout(0, newTop - mRefreshHeaderView.getMeasuredHeight() - mMiddleView.getMeasuredHeight(), mRefreshHeaderView.getRight(), newTop - mMiddleView.getHeight());
                mMiddleView.layout(0, newTop - mMiddleView.getMeasuredHeight(), mMiddleView.getRight(), newTop);
                mBottomView.layout(0, newTop, mBottomView.getRight(), mBottomView.getMeasuredHeight());
            }
        }

        @Override
        public void onViewReleased(@NonNull View releasedChild, float xvel, float yvel) {
            super.onViewReleased(releasedChild, xvel, yvel);

        }
    };
}
