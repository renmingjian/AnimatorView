package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.widget.RelativeLayout;

/**
 * Created by mj on 2020/8/18 14:53
 */
public class RelativeLayout2 extends RelativeLayout {
    public RelativeLayout2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("event--dispatchTouchEvent--RelativeLayout2");
        return false;
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("event--onTouchEvent--RelativeLayout2");
        return super.onTouchEvent(event);
    }
}
