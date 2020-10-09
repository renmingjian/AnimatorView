package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatTextView;

/**
 * Created by mj on 2020/10/9 11:19
 */
public class MyTextView extends AppCompatTextView {

    public MyTextView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        System.out.println("onMeasure");
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
    }
}
