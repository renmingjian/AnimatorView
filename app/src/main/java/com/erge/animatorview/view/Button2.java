package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.MotionEvent;

import androidx.appcompat.widget.AppCompatButton;

/**
 * Created by mj on 2020/8/18 14:56
 */
public class Button2 extends AppCompatButton {
    public Button2(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent ev) {
        System.out.println("event--dispatchTouchEvent" + ev.getActionMasked() + "--Button2");
        return super.dispatchTouchEvent(ev);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        System.out.println("event--onTouchEvent--" + event.getActionMasked() + "--Button2");
        return super.onTouchEvent(event);
    }
}
