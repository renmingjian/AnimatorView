package com.erge.animatorview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.HorizontalScrollView;

import com.erge.animatorview.adapter.CarControlAdapter;

/**
 * Created by erge 2020/5/19 9:37 AM
 */
public class CarControlScrollView extends HorizontalScrollView {

    private CarControlGridLayout controlCarGridLayout;

    public CarControlScrollView(Context context) {
        super(context);
    }

    public CarControlScrollView(Context context, AttributeSet attrs) {
        super(context, attrs);
        controlCarGridLayout = new CarControlGridLayout(context);
        addView(controlCarGridLayout);
    }

    public void setAdapter(CarControlAdapter adapter) {
        controlCarGridLayout.setAdapter(adapter);
    }

    public void setOnItemClickListener(CarControlGridLayout.OnItemClickListener onItemClickListener) {
        controlCarGridLayout.setOnItemClickListener(onItemClickListener);
    }

    @Override
    public boolean onTouchEvent(MotionEvent ev) {
        int childCount = controlCarGridLayout.getChildCount();
        if (ev.getActionMasked() == MotionEvent.ACTION_UP && childCount > 8) {
            // getScrollX() / getWidth() + 1:表示当前处于第几页，从1开始
            // limit表示：从当前页向下一页滑动的临界点，如从第一页到第二页临界点为getWidth() / 2
            //                                     从第一页到第二页临界点为getWidth() / 2 * 3
            // 如果当前的scrollX大于临界点，则向下一页滑动，否则向上一页滑动
            int limit = (getScrollX() / getWidth() + 1) * getWidth() - getWidth() / 2;
            if (getScrollX() >= limit) {
                runAnimate(limit + getWidth() / 2);
            } else {
                runAnimate(limit - getWidth() / 2);
            }
        }
        return super.onTouchEvent(ev);
    }

    @Override
    public void fling(int velocityX) {
        // 禁止掉惯性滑动
    }

    private void runAnimate(final int end) {
        ValueAnimator animator = ValueAnimator.ofInt(getScrollX(), end);
        animator.setDuration(400);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                int x = (int) animation.getAnimatedValue();
                scrollTo(x, 0);
            }
        });
        animator.start();
    }

}
