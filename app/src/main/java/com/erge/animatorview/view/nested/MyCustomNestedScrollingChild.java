package com.erge.animatorview.view.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.LinearLayout;

import androidx.annotation.Nullable;
import androidx.core.view.NestedScrollingChild;
import androidx.core.view.NestedScrollingChildHelper;
import androidx.core.view.ViewCompat;

/**
 * Created by erge 3/26/21 1:54 PM
 */
public class MyCustomNestedScrollingChild extends LinearLayout implements NestedScrollingChild {

    private NestedScrollingChildHelper mNestedScrollingChildHelper;
    private final int[] offset = new int[2]; //偏移量
    private final int[] consumed = new int[2]; //消费
    private int lastY;

    public MyCustomNestedScrollingChild(Context context) {
        super(context);
    }

    public MyCustomNestedScrollingChild(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public MyCustomNestedScrollingChild(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        switch (event.getAction()) {
            case MotionEvent.ACTION_DOWN:
                //记录触摸时的Y轴方向
                lastY = (int) event.getRawY();
                break;
            case MotionEvent.ACTION_MOVE:
                int y = (int) (event.getRawY());
                int dy = y - lastY;//dy为屏幕上滑动的偏移量
                lastY = y;
                dispatchNestedPreScroll(0, dy, consumed, offset);
                startNestedScroll(ViewCompat.SCROLL_AXIS_VERTICAL);
                break;
        }

        return true;
    }

    //初始化helper对象
    private NestedScrollingChildHelper getScrollingChildHelper() {
        if (mNestedScrollingChildHelper == null) {
            mNestedScrollingChildHelper = new NestedScrollingChildHelper(this);
            mNestedScrollingChildHelper.setNestedScrollingEnabled(true);
        }
        return mNestedScrollingChildHelper;
    }

    @Override
    public void setNestedScrollingEnabled(boolean enabled) {        //设置滚动事件可用性
        getScrollingChildHelper().setNestedScrollingEnabled(enabled);
    }

    @Override
    public boolean isNestedScrollingEnabled() {//是否可以滚动
        return getScrollingChildHelper().isNestedScrollingEnabled();
    }

    @Override
    public boolean startNestedScroll(int axes) {//开始滚动
        return getScrollingChildHelper().startNestedScroll(axes);
    }

    @Override
    public void stopNestedScroll() {//停止滚动，清空滚动状态
        getScrollingChildHelper().stopNestedScroll();
    }

    @Override
    public boolean hasNestedScrollingParent() {//判断是否含有对应的NestedScrollingParent
        return getScrollingChildHelper().hasNestedScrollingParent();
    }

    @Override
    public boolean dispatchNestedScroll(int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed, int[] offsetInWindow) {
        //在子view进行滚动之后调用此方法，询问父view是否还要进行余下(unconsumed)的滚动。
        //前四个参数为输入参数，用于告诉父view已经消费和尚未消费的距离，最后一个参数为输出参数，用于子view获取父view位置的偏移量。
        //如果父view接收了它的滚动参数，进行了部分消费，则这个函数返回true，否则为false
        return getScrollingChildHelper().dispatchNestedScroll(dxConsumed, dyConsumed, dxUnconsumed, dyUnconsumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedPreScroll(int dx, int dy, int[] consumed, int[] offsetInWindow) {
        //在子view自己进行滚动之前调用此方法，询问父view是否要在子view之前进行滚动。
        //此方法的前两个参数用于告诉父View此次要滚动的距离；而第三第四个参数用于子view获取父view消费掉的距离和父view位置的偏移量。
        return getScrollingChildHelper().dispatchNestedPreScroll(dx, dy, consumed, offsetInWindow);
    }

    @Override
    public boolean dispatchNestedFling(float velocityX, float velocityY, boolean consumed) {
        return getScrollingChildHelper().dispatchNestedFling(velocityX, velocityY, consumed);
    }

    @Override
    public boolean dispatchNestedPreFling(float velocityX, float velocityY) {
        return getScrollingChildHelper().dispatchNestedPreFling(velocityX, velocityY);
    }

}
