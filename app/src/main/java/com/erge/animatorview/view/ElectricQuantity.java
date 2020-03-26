package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.RadialGradient;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

/**
 * Created by erge 2020-03-16 10:44
 */
public class ElectricQuantity extends View {

    // 圆圈粗细
    private static final int CIRCLE_OUT = (int) Utils.dp2px(17);
    private static final int CIRCLE_CENTER = (int) Utils.dp2px(11);
    private static final int CIRCLE_INSIDE = (int) Utils.dp2px(10);


    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private int mWidth;
    private int mHeight;
    private int mOutRadius;
    private int mCenterRadius;
    private int mInsideRadius;
    private int mTextSize;

    public ElectricQuantity(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        initAttrs(context, attrs);
    }

//    private void initAttrs(Context context, AttributeSet attrs) {
//
//    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getMeasuredWidth();
        mHeight = getMeasuredHeight();
        mOutRadius = mWidth / 2 - CIRCLE_OUT / 2;
        mCenterRadius = mWidth / 2 - CIRCLE_OUT - CIRCLE_CENTER / 2;
        mOutRadius = mWidth / 2 - CIRCLE_OUT - CIRCLE_CENTER - CIRCLE_INSIDE / 2;
        mTextSize = mWidth / 3;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        canvas.translate(mWidth / 2, mHeight / 2);
        drawOutCircle(canvas);
        canvas.restore();
    }

    private void drawOutCircle(Canvas canvas) {
        double startAngle = 135d / 180d * Math.PI;
        double endAngle = Math.PI;
        float startX = (float) (Math.cos(startAngle) * mOutRadius);
        float startY = (float) (Math.sin(startAngle) * mOutRadius);
        float endX = (float) (Math.cos(endAngle) * mOutRadius);
        float endY = (float) (Math.sin(endAngle) * mOutRadius);

        RectF rectF = new RectF(-mOutRadius, -mOutRadius, mOutRadius, mOutRadius);
        mPaint.setShader(null);
        LinearGradient gradient1 = new LinearGradient(startX, startY, endX, endY,
                new int[]{0xFFFFFFFF, 0xFFDFECFF}, new float[]{0f, 1f},
                Shader.TileMode.CLAMP);
        mPaint.setShader(gradient1);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(CIRCLE_OUT);

//        canvas.drawArc(rectF, 135f, 45f, false, mPaint);

        RadialGradient radialGradient = new RadialGradient(0, 0, mOutRadius + CIRCLE_OUT / 2,
                new int[]{0xffffffff, 0xffffffff, 0xFFDFECFF}, new float[]{0f, 0.9f, 1f},
                Shader.TileMode.CLAMP);

        RectF rectF1 = new RectF(-mOutRadius, -mOutRadius, mOutRadius, mOutRadius);
        // 画最内层圆
        mPaint.setShader(radialGradient);
        canvas.drawArc(rectF1, 135f, 270f, false, mPaint);

        float delta = (float) Math.sqrt(CIRCLE_OUT / 2);
        float outRadialLeftRadius = mOutRadius - CIRCLE_OUT;
        final RectF outRadialLeftRectF = new RectF(-delta - outRadialLeftRadius,
                delta - outRadialLeftRadius,
                -delta + outRadialLeftRadius,
                delta + outRadialLeftRadius);

        RadialGradient outRadialLeftGradient = new RadialGradient(-delta, delta, outRadialLeftRadius  + CIRCLE_OUT / 2,
                new int[]{0xffffffff, 0xffffffff, 0xFFDFECFF}, new float[]{0f, 0.9f, 1f},
                Shader.TileMode.CLAMP);
        mPaint.setShader(outRadialLeftGradient);
        canvas.drawArc(outRadialLeftRectF, 135f, 45f, false, mPaint);
//        canvas.drawCircle(-delta, delta, outRadialLeftRadius, mPaint);
//        mPaint.setColor(Color.parseColor("#55ff0000"));
//        canvas.drawCircle(0,0, outRadialLeftRadius, mPaint);

//        mPaint.setShader(null);
//        mPaint.setShader(radialGradient);
//        mPaint.setColor(Color.parseColor("#FFDFECFF"));
//        canvas.drawArc(rectF, 180f, 180f, false, mPaint);

//        double startAngle2 = 2 * Math.PI;
//        double endAngle2 = 405d / 180d * Math.PI;
//        float startX2 = (float) (Math.cos(startAngle2) * mOutRadius);
//        float startY2 = (float) (Math.sin(startAngle2) * mOutRadius);
//        float endX2 = (float) (Math.cos(endAngle2) * mOutRadius);
//        float endY2 = (float) (Math.sin(endAngle2) * mOutRadius);
//        LinearGradient gradient3 = new LinearGradient(startX2, startY2, endX2, endY2,
//                0xffDFECFF, 0x00DFECFF, Shader.TileMode.CLAMP);
//        mPaint.setShader(gradient3);
//        canvas.drawArc(rectF, 360f, 45f, false, mPaint);


    }

    private void drawCenterCircle(Canvas canvas) {

    }

    private void drawInsideCircle(Canvas canvas) {

    }

    private void drawInsideText(Canvas canvas) {

    }
}
