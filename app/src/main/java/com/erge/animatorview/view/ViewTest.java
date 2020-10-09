package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.View;
import android.widget.FrameLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

/**
 * Created by mj on 2020/9/16 14:11
 */
public class ViewTest extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    private static final String TAG = ViewTest.class.getName();

    public ViewTest(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#ff0000"));
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        System.out.println(TAG + "-onMeasure");
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.drawRect(new Rect(0, 0, getWidth(), getHeight()), mPaint);
        System.out.println(TAG + "-onDraw");
    }

    public void update() {
        mPaint.setColor(Color.parseColor("#00ff00"));
        invalidate();
    }

}
