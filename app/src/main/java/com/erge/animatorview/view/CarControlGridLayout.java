package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import com.erge.animatorview.adapter.CarControlAdapter;

/**
 * C62控制项item排序。每页最多展示16条数据
 * Created by erge 2020/5/18 9:40 AM
 */
public class CarControlGridLayout extends FrameLayout implements View.OnClickListener {

    // 每行4个
    private static final int ROW_COUNT = 4;
    // 每页8个
    private static final int PAGE_COUNT = 8;

    private int childWidth;
    private int parentWidth;
    private OnItemClickListener onItemClickListener;

    public CarControlGridLayout(Context context) {
        super(context);
    }

    public CarControlGridLayout(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int childCount = getChildCount();
        int childHeight = 0;
        for (int i = 0; i < childCount; i++) {
            measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
            childHeight = getChildAt(0).getMeasuredHeight();
        }
        int width = MeasureSpec.getSize(widthMeasureSpec);
        // 如果child数量大于8，则需要用2页展示，如果大于16则用3页展示，父View的宽度为一页宽度的两倍
        width = width * (1 + childCount / PAGE_COUNT);

        // 如果child数量大于4，则需要用2行展示，父View的高度为一行高度的两倍
        if (childCount > ROW_COUNT) {
            childHeight *= 2;
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(childHeight, heightMeasureSpec));
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        parentWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        childWidth = parentWidth / ROW_COUNT;
        final MarginLayoutParams lp = (MarginLayoutParams) child.getLayoutParams();

        // 因为宽度不是自适应也不是xml中写死的值，需要是父类的四分之一，所以在测量子View的宽度时，需要自己先计算
        // 出来的，然后根据具体的宽度拿到子View的测量规则
        final int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec,
                getPaddingLeft() + getPaddingRight() + lp.leftMargin + lp.rightMargin
                        + widthUsed, childWidth);

        // 高度是自适应，所以直接通过父类的LayoutParams可以拿到
        final int childHeightMeasureSpec = getChildMeasureSpec(parentHeightMeasureSpec,
                getPaddingTop() + getPaddingBottom() + lp.topMargin + lp.bottomMargin
                        + heightUsed, lp.height);

        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        final int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            layoutChild(i);
        }
    }

    /**
     * 摆放前八个item
     * -----l           t           r           b
     * 0    0           0           1 * w       1 * h
     * 1    1 * w       0           2 * w       1 * h
     * 2    2 * w       0           3 * w       1 * h
     * 3    3 * w       0           4 * w       1 * h
     * 4    0           1 * h       1 * w       2 * h
     * 5    1 * w       1 * h       2 * w       2 * h
     * ...
     * 8    0 * w + pw  0           1 * w + pw  1 * h
     * 9    1 * w + pw  0           2 * w + pw  1 * h
     *
     * @param i 索引
     */
    private void layoutChild(int i) {
        final View child = getChildAt(i);
        int l = ((i - i / PAGE_COUNT * PAGE_COUNT) % ROW_COUNT) * childWidth + i / PAGE_COUNT * parentWidth;
        int t = ((i - i / PAGE_COUNT * PAGE_COUNT) / ROW_COUNT) * child.getMeasuredHeight();
        int r = l + childWidth;
        int b = t + child.getMeasuredHeight();
        child.layout(l, t, r, b);
    }

    public void setAdapter(CarControlAdapter adapter) {
        int childCount = adapter.getCount();
        if (childCount == 0) return;
        removeAllViews();
        for (int i = 0; i < childCount; i++) {
            View child = adapter.getView(i, null, this);
            child.setTag(i);
            child.setOnClickListener(this);
            addView(child);
        }
    }

    @Override
    public void onClick(View v) {
        if (onItemClickListener != null) {
            int position = (int) v.getTag();
            onItemClickListener.onItemClick(position, v);
        }
    }

    public void setOnItemClickListener(OnItemClickListener onItemClickListener) {
        this.onItemClickListener = onItemClickListener;
    }

    public interface OnItemClickListener {
        void onItemClick(int position, View itemView);
    }

}
