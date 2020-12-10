package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.annotation.SuppressLint;
import android.content.Context;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.BounceInterpolator;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * 悬浮View
 * Created by erge 12/10/20 11:33 AM
 */
public class FloatingView extends FrameLayout {

    // 视为点击事件的距离
    private static final int CUSTOMER_TOUCH_SLOP = 12;

    private float mDownX, mDownY;
    private Builder mBuilder;

    private FloatingView(@NonNull Context context) {
        super(context);
    }

    public FloatingView(@NonNull Context context, @Nullable Builder builder) {
        this(context);
        mBuilder = builder;
        init();
    }

    private void init() {
        attachRootView(mBuilder.attachRootLayout);
        setContentView(mBuilder.contentView);
    }

    public void attachRootView(ViewGroup viewGroup) {
        if (viewGroup != null) {
            viewGroup.addView(this);
        }
    }

    public void setContentView(View view) {
        if (view == null) return;
        removeAllViews();
        addView(view);
    }

    @Override
    protected void onLayout(boolean changed, int left, int top, int right, int bottom) {
        super.onLayout(changed, left, top, right, bottom);
        // 初始位置确定--右下角
        setX(getXLimit(mBuilder.attachRootLayout.getWidth()));
        setY(getYLimit(mBuilder.attachRootLayout.getHeight()));
    }

    @SuppressLint("ClickableViewAccessibility")
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (mBuilder.canDrag) {
            switch (event.getActionMasked()) {
                case MotionEvent.ACTION_DOWN:
                    mDownX = event.getRawX();
                    mDownY = event.getRawY();
                    break;
                case MotionEvent.ACTION_MOVE:
                    move(event);
                    break;
                case MotionEvent.ACTION_UP:
                case MotionEvent.ACTION_CANCEL:
                    up(event);
                    break;
            }
            return true;
        }
        return super.onTouchEvent(event);
    }

    /**
     * 移动
     */
    private void move(MotionEvent event) {
        final float moveX = event.getRawX();
        final float moveY = event.getRawY();
        if (!(Math.abs(moveX - mDownX) <= CUSTOMER_TOUCH_SLOP) || !(Math.abs(moveY - mDownY) <= CUSTOMER_TOUCH_SLOP)) {
            setX(getXLimit(moveX));
            setY(getYLimit(moveY));
        }
    }

    /**
     * 手指抬起
     */
    private void up(MotionEvent event) {
        float upX = event.getRawX();
        float upY = event.getRawY();
        // 触发点击事件
        if (Math.abs(upX - mDownX) <= CUSTOMER_TOUCH_SLOP && Math.abs(upY - mDownY) <= CUSTOMER_TOUCH_SLOP) {
            if (mBuilder.onClickListener != null) {
                mBuilder.onClickListener.onClick(this);
            } else {
                performClick();
            }
        } else {
            ObjectAnimator animator;
            if (upX >= mBuilder.attachRootLayout.getWidth() >> 1) {
                animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X,
                        upX, mBuilder.attachRootLayout.getWidth() - mBuilder.contentView.getWidth() - mBuilder.marginX);
            } else {
                animator = ObjectAnimator.ofFloat(this, View.TRANSLATION_X, getX(), mBuilder.marginX);
            }
            animator.setDuration(500);
            animator.setInterpolator(new BounceInterpolator());
            animator.start();
        }
    }

    /**
     * X轴上的限制
     * @param currentX 当前位置
     */
    private float getXLimit(float currentX) {
        float x = currentX - (mBuilder.contentView.getWidth() >> 1);
        if (x < mBuilder.marginX) x = mBuilder.marginX;
        if (x > (mBuilder.attachRootLayout.getWidth() - mBuilder.contentView.getWidth()) - mBuilder.marginX) {
            x = mBuilder.attachRootLayout.getWidth() - mBuilder.contentView.getWidth() - mBuilder.marginX;
        }
        return x;
    }

    /**
     * Y轴上的限制
     * @param currentY 当前位置
     */
    private float getYLimit(float currentY) {
        float y = currentY - mBuilder.contentView.getHeight();
        if (y < mBuilder.marginY) y = mBuilder.marginY;
        if (y > (mBuilder.attachRootLayout.getHeight() - mBuilder.contentView.getHeight()) - mBuilder.marginY) {
            y = mBuilder.attachRootLayout.getHeight() - mBuilder.contentView.getHeight() - mBuilder.marginY;
        }
        return y;
    }

    public static class Builder {
        // 悬浮View需要放在哪个页面布局上
        private ViewGroup attachRootLayout;
        // 悬浮View内承载的具体视图
        private View contentView;
        // 在X轴方向的边距
        private float marginX;
        // 在Y轴方向的边距
        private float marginY;
        // 整个悬浮View的点击事件
        private View.OnClickListener onClickListener;
        // 是否可以拖动
        private boolean canDrag = true;

        public Builder attachRootLayout(ViewGroup attachRootLayout) {
            this.attachRootLayout = attachRootLayout;
            return this;
        }

        public Builder setContentView(View view) {
            contentView = view;
            return this;
        }

        public Builder setMarginX(float marginX) {
            this.marginX = marginX;
            return this;
        }

        public Builder setMarginY(float marginY) {
            this.marginY = marginY;
            return this;
        }

        public Builder setOnClickListener(OnClickListener onClickListener) {
            this.onClickListener = onClickListener;
            return this;
        }

        public Builder setCanDrag(boolean canDrag) {
            this.canDrag = canDrag;
            return this;
        }

        public FloatingView build(Context context) {
            return new FloatingView(context, this);
        }
    }

}
