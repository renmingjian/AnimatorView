package com.erge.animatorview.view;

import android.content.Context;
import android.text.Layout;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.customview.widget.ViewDragHelper;

/**
 * Created by erge 12/25/20 2:39 PM
 */
public class RefreshLayout extends FrameLayout {

    private final ViewDragHelper mDragHelper;
    // 刷新头部
    private RefreshHeaderView mRefreshHeaderView;
    // 内容区域
    private View mContentView;
    private int mHeaderHeight, mContentViewHeight;
    private int mDragRange = 600;


    public RefreshLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDragHelper = ViewDragHelper.create(this, callback);
    }

    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();
        if (getChildCount() == 0) return;
        View view = getChildAt(0);
        if (view instanceof RefreshHeaderView) {
            mRefreshHeaderView = (RefreshHeaderView) view;
            mContentView = getChildAt(1);
        } else {
            mRefreshHeaderView = new DefaultRefreshHeaderView(getContext());
            LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
            mRefreshHeaderView.setLayoutParams(layoutParams);
            addView(mRefreshHeaderView, 0);
            mContentView = view;
        }
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        mHeaderHeight = mRefreshHeaderView.getMeasuredHeight();
        mContentViewHeight = mContentView.getMeasuredHeight();
        mRefreshHeaderView.layout(0, -mHeaderHeight, getWidth(), 0);
        mContentView.layout(0, 0, getWidth(), mContentViewHeight);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        if (mRefreshHeaderView.getBottom() == 0) {
            return super.dispatchTouchEvent(ev);
        }
        return mDragHelper.shouldInterceptTouchEvent(ev);
    }

//    @Override
//    public boolean onTouchEvent(MotionEvent event) {
//        mDragHelper.processTouchEvent(event);
//        return true;
//    }

    private final ViewDragHelper.Callback callback = new ViewDragHelper.Callback() {
        @Override
        public boolean tryCaptureView(@NonNull View child, int pointerId) {
            return child == mContentView;
        }

        @Override
        public int getViewVerticalDragRange(@NonNull View child) {
            return mDragRange;
        }

        @Override
        public int clampViewPositionHorizontal(@NonNull View child, int left, int dx) {
            return 0;
        }

        @Override
        public int clampViewPositionVertical(@NonNull View child, int top, int dy) {
            System.out.println("top === " + top);
            return top;
        }

        @Override
        public void onViewPositionChanged(@NonNull View changedView, int left, int top, int dx, int dy) {
            mContentView.layout(0, top, getWidth(), top + mContentViewHeight);
        }
    };

}
