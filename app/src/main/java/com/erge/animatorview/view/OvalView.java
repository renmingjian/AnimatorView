package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by erge 2020-03-26 15:05
 */
public class OvalView extends View {

    // 黑色
    private static final int color1 = 0xff14171d;
    // 浅色
    private static final int color2 = 0xfff8f8f8;

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public OvalView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(color1);
        mPaint.setStyle(Paint.Style.FILL);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.drawRect(0, 0, getMeasuredWidth(), getMeasuredHeight() / 3f * 2f, mPaint);
        canvas.drawOval(-50, getMeasuredHeight() / 3f, getMeasuredWidth() + 50, getMeasuredHeight(), mPaint);
        canvas.restore();
    }

    public void switchColor(boolean up) {

        int colorOrigin = mPaint.getColor();
        int colorNow = up ? color2 : color1;
        if (colorOrigin != colorNow) {
            mPaint.setColor(colorNow);
            invalidate();
        }
    }

}
