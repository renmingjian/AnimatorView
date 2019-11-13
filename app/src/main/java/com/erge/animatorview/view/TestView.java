package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * Created by erge 2019-11-13 14:50
 */
public class TestView extends View {

    public TestView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
        paint.setColor(Color.parseColor("#ff0000"));
        paint.setStrokeWidth(5);
        RectF rectF = new RectF(0f, 0f, 300f, 300f);
        Path path = new Path();
        path.addArc(rectF, 120f, 300f);
//        path.addCircle(200f, 200f, 100f, Path.Direction.CCW);
        canvas.drawPath(path, paint);
        System.out.println("width = " + getWidth() + "--height = " + getHeight());
//        canvas.drawRect(0f, 0f, getWidth(), getHeight(), paint);
    }
}
