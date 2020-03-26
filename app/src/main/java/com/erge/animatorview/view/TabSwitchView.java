package com.erge.animatorview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.viewpager.widget.PagerAdapter;
import androidx.viewpager.widget.ViewPager;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erge 2020-03-18 10:46
 */
public class TabSwitchView extends HorizontalScrollView {

    public static final int INDICATOR_WIDTH = (int) Utils.dp2px(36);

    private final LinearLayout container;
    private ViewPager viewPager;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    List<TabView> list = new ArrayList<>();
    private final int tabMargin;
    private final int indicatorHeight;
    private int currentItem;
    private int tabWidth;
    private int count;
    private RectF rectF = new RectF();
    private float mTranslationX;

    {
        paint.setColor(Color.parseColor("#4a4a4a"));
        paint.setAntiAlias(true);
        paint.setStyle(Paint.Style.FILL);
    }

    public TabSwitchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.TabSwitchView);
        tabMargin = (int) typedArray.getDimension(R.styleable.TabSwitchView_tabMargin, Utils.dp2px(30));
        indicatorHeight = (int) typedArray.getDimension(R.styleable.TabSwitchView_indicatorHeight, Utils.dp2px(2));
        typedArray.recycle();
        container = new LinearLayout(context);
        container.setOrientation(LinearLayout.HORIZONTAL);
        addView(container);
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        if (count != 0) {
            tabWidth = getMeasuredWidth() / count;
        }
    }

    @Override
    protected void dispatchDraw(Canvas canvas) {
        super.dispatchDraw(canvas);
        canvas.drawRect(30, getHeight() - 10, 150, getHeight(), paint);
    }

    public void setCustomerTabs(List<TabView> list) {
        if (list == null || list.isEmpty()) return;
        container.removeAllViews();
        for (int i = 0; i < list.size(); i++) {
            container.addView(list.get(i).tabView);
        }
    }

    public void setUpWithViewPager(ViewPager viewPager) {
        this.viewPager = viewPager;
        initTabs();
        viewPager.addOnPageChangeListener(new ViewPager.OnPageChangeListener() {
            @Override
            public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {
                System.out.println("position = " + position + "positionOffset = " + positionOffset + "--" + positionOffsetPixels);
                update(position, positionOffset);
            }

            @Override
            public void onPageSelected(int position) {
                currentItem = position;
            }

            @Override
            public void onPageScrollStateChanged(int state) {

            }
        });
    }

    private void update(int position, float positionOffset) {
        TabView tabView = list.get(position);
        View currentView = tabView.tabView;
        int width = currentView.getWidth();
        int left = (currentView.getLeft() + currentView.getRight()) / 2 - INDICATOR_WIDTH / 2;
        float originRight = (currentView.getLeft() + currentView.getRight()) / 2 + INDICATOR_WIDTH / 2;
//        float right =
        // 不断改变偏移量，invalidate
//        mTranslationX = getWidth() / mTabVisibleCount * (position + offset);
//
//        int tabWidth = width / mTabVisibleCount;
//
//        // 容器滚动，当移动到倒数最后一个的时候，开始滚动
//        if (offset > 0 && position >= (mTabVisibleCount - 2)
//                && getChildCount() > mTabVisibleCount) {
//            if (mTabVisibleCount != 1) {
//                this.scrollTo((position - (mTabVisibleCount - 2)) * tabWidth
//                        + (int) (tabWidth * offset), 0);
//            } else
//            // 为count为1时 的特殊处理
//            {
//                this.scrollTo(
//                        position * tabWidth + (int) (tabWidth * offset), 0);
//            }
//        }

        invalidate();
    }

    public void setCurrentItem(int currentItem) {
        this.currentItem = currentItem;
        if (viewPager != null) {
            viewPager.setCurrentItem(currentItem);
        }
        invalidate();
    }

    private void initTabs() {
        PagerAdapter adapter = viewPager.getAdapter();
        if (adapter == null) {
            throw new NullPointerException("在调用setUpWithViewPager之前需要先给ViewPager设置Adapter");
        }
        count = adapter.getCount();
        list.clear();
        for (int i = 0; i < count; i++) {
            TabView tabView = new TabView();
            tabView.index = i;
            tabView.desc = adapter.getPageTitle(i).toString();
            tabView.tabView = createTextView(tabView.desc, i, count);
            list.add(tabView);
        }
        setCustomerTabs(list);
    }

    private TextView createTextView(String title, int index, int count) {
        TextView tv = new TextView(getContext());
        LayoutParams lp = new LayoutParams(
                LayoutParams.WRAP_CONTENT, LayoutParams.MATCH_PARENT);
        lp.leftMargin = tabMargin;
        if (index == count - 1) {
            lp.rightMargin = tabMargin;
        }
        tv.setGravity(Gravity.CENTER);
        tv.setTextColor(Color.parseColor("#4a4a4a"));
        tv.setText(title);
        tv.setTextSize(TypedValue.COMPLEX_UNIT_SP, 14);
        tv.setLayoutParams(lp);
        return tv;
    }

    public class TabView {
        View tabView;
        String desc;
        int index;

        public View getTabView() {
            return tabView;
        }

        public void setTabView(View tabView) {
            this.tabView = tabView;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public int getIndex() {
            return index;
        }

        public void setIndex(int index) {
            this.index = index;
        }
    }

}
