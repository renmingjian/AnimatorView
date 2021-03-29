package com.erge.animatorview.view.nested;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.core.view.NestedScrollingParent;
import androidx.core.view.NestedScrollingParentHelper;

/**
 * Created by erge 3/26/21 1:55 PM
 */
public class MyCustomNestedScrollingParent extends LinearLayout implements NestedScrollingParent {

    private ImageView img;
    private TextView tv;
    private MyCustomNestedScrollingChild myNestedScrollChild;
    private NestedScrollingParentHelper mNestedScrollingParentHelper;
    private int imgHeight;
    private int tvHeight;


    public MyCustomNestedScrollingParent(Context context) {
        super(context);
    }

    public MyCustomNestedScrollingParent(Context context, AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        mNestedScrollingParentHelper = new NestedScrollingParentHelper(this);
    }

    //当view加载完成时获取子view
    @Override
    protected void onFinishInflate() {
        super.onFinishInflate();

        //获取第一个子view，ImageView
        img = (ImageView) getChildAt(0);

        //获取第二个子view，TextView
        tv = (TextView) getChildAt(1);

        //获取第三个子view，MyCustomNestedScrollingChild
        myNestedScrollChild = (MyCustomNestedScrollingChild) getChildAt(2);

        img.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //当布局变化时，获取图片布局的高度
                if (imgHeight <= 0) {
                    imgHeight = img.getMeasuredHeight();
                }
            }
        });
        tv.getViewTreeObserver().addOnGlobalLayoutListener(new ViewTreeObserver.OnGlobalLayoutListener() {
            @Override
            public void onGlobalLayout() {
                //当布局变化时，获取文字布局的高度
                if (tvHeight <= 0) {
                    tvHeight = tv.getMeasuredHeight();
                }
            }
        });
    }

    //在此可以判断参数target是哪一个子view以及滚动的方向，然后决定是否要配合其进行嵌套滚动
    @Override
    public boolean onStartNestedScroll(View child, View target, int nestedScrollAxes) {
        if (target instanceof MyCustomNestedScrollingChild) {
            return true;
        }
        return false;
    }


    @Override
    public void onNestedScrollAccepted(View child, View target, int nestedScrollAxes) {
        mNestedScrollingParentHelper.onNestedScrollAccepted(child, target, nestedScrollAxes);
    }

    @Override
    public void onStopNestedScroll(View target) {
        mNestedScrollingParentHelper.onStopNestedScroll(target);
    }

    //先于child滚动
    //前3个为输入参数，最后一个是输出参数
    @Override
    public void onNestedPreScroll(View target, int dx, int dy, int[] consumed) {

        if (showImg(dy) || hideImg(dy)) {//根据图片的高度判断上拉和下拉的处理
            scrollBy(0, -dy);
            consumed[1] = dy;//告诉child消费了多少距离
        }

    }

    //后于child滚动
    @Override
    public void onNestedScroll(View target, int dxConsumed, int dyConsumed, int dxUnconsumed, int dyUnconsumed) {

    }

    @Override
    public boolean onNestedPreFling(View target, float velocityX, float velocityY) {
        //是否消费了手指滑动事件
        return false;
    }

    @Override
    public boolean onNestedFling(View target, float velocityX, float velocityY, boolean consumed) {
        //是否消费了手指滑动事件
        return false;
    }

    @Override
    public int getNestedScrollAxes() {
        return mNestedScrollingParentHelper.getNestedScrollAxes();
    }

    //下拉的时候是否要向下滚动以显示图片
    public boolean showImg(int dy) {
        if (dy > 0) {
            if (getScrollY() > 0 && myNestedScrollChild.getScrollY() == 0) {
                return true;
            }
        }

        return false;
    }

    //上拉的时候，是否要向上滚动，隐藏图片
    public boolean hideImg(int dy) {
        if (dy < 0) {
            if (getScrollY() < imgHeight) {
                return true;
            }
        }
        return false;
    }

    //限制滚动范围，防止出现偏差
    @Override
    public void scrollTo(int x, int y) {
        if (y < 0) {
            y = 0;
        }
        if (y > imgHeight) {
            y = imgHeight;
        }

        super.scrollTo(x, y);
    }

}
