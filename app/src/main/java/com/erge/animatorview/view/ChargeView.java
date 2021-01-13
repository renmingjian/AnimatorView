package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.BlurMaskFilter;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.RectF;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;


/**
 * Created by erge 2019-10-28 19:24
 */
public class ChargeView extends View {

    private static final float MAX_RADIUS = Utils.dp2px(130);
    private static final float MIDDLE_RADIUS = Utils.dp2px(110);
    private static final float MINI_RADIUS = Utils.dp2px(120);
    private static final int COLOR_PURPLE = 0xff9800C4;
    private static final int COLOR_BLUE = 0xff2227AD;
    private static final int COLOR_BLUE_DARK = 0xff270A72;
    private static final float INNER_CIRCLE_RADIUS = Utils.dp2px(80);

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint.FontMetrics mMetrics = new Paint.FontMetrics();
    private float translate1, translate2, translate3;
    private int text = 50;


    public ChargeView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
//        setBackgroundColor(Color.parseColor("#0a0206"));
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
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        drawOutCircle(canvas);
        drawInnerCircle(canvas);
        drawInnerText(canvas);
        drawLine(canvas);
        canvas.restore();
    }

    private void drawOutCircle(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(COLOR_PURPLE);
        mPaint.setStrokeWidth(Utils.dp2px(3));
        mPaint.setStyle(Paint.Style.STROKE);

        RectF rectF1 = new RectF(-MAX_RADIUS, -MAX_RADIUS, MAX_RADIUS, MAX_RADIUS);
        RectF rectF2 = new RectF(-MIDDLE_RADIUS, -MIDDLE_RADIUS, MIDDLE_RADIUS, MIDDLE_RADIUS);
        RectF rectF3 = new RectF(-MINI_RADIUS, -MINI_RADIUS, MINI_RADIUS, MINI_RADIUS);

        float leftStartCos = (float) Math.cos(150d * Math.PI / 180d);
        float leftStartSin = (float) Math.sin(150d * Math.PI / 180d);
        float leftEndCos = (float) Math.cos(210d * Math.PI / 180d);
        float leftEndSin = (float) Math.sin(210d * Math.PI / 180d);
        LinearGradient linearGradient1 = new LinearGradient(leftStartCos * MAX_RADIUS,
                leftStartSin * MAX_RADIUS, leftEndCos * MAX_RADIUS, leftEndSin * MAX_RADIUS,
                COLOR_BLUE, COLOR_PURPLE, Shader.TileMode.CLAMP);
        LinearGradient linearGradient2 = new LinearGradient(leftStartCos * MIDDLE_RADIUS,
                leftStartSin * MIDDLE_RADIUS, leftEndCos * MIDDLE_RADIUS, leftEndSin * MIDDLE_RADIUS,
                COLOR_BLUE, COLOR_PURPLE, Shader.TileMode.CLAMP);
        LinearGradient linearGradient3 = new LinearGradient(leftStartCos * MINI_RADIUS,
                leftStartSin * MINI_RADIUS, leftEndCos * MINI_RADIUS, leftEndSin * MINI_RADIUS,
                COLOR_BLUE, COLOR_PURPLE, Shader.TileMode.CLAMP);

        canvas.drawArc(rectF1, 210, 120, false, mPaint);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawArc(rectF2, 210, 120, false, mPaint);
        canvas.drawArc(rectF3, 210, 120, false, mPaint);


        mPaint.setStrokeWidth(Utils.dp2px(3));
        mPaint.setShader(linearGradient1);
        canvas.drawArc(rectF1, 150, 60, false, mPaint);
        mPaint.setShader(linearGradient1);
        canvas.drawArc(rectF1, 330, 60, false, mPaint);

        mPaint.setStrokeWidth(Utils.dp2px(2));
        mPaint.setShader(linearGradient2);
        canvas.drawArc(rectF2, 150, 60, false, mPaint);
        mPaint.setShader(linearGradient2);
        canvas.drawArc(rectF2, 330, 60, false, mPaint);

        mPaint.setShader(linearGradient3);
        canvas.drawArc(rectF3, 150, 60, false, mPaint);
        mPaint.setShader(linearGradient3);
        canvas.drawArc(rectF3, 330, 60, false, mPaint);

        mPaint.setStrokeWidth(Utils.dp2px(3));
        mPaint.setColor(COLOR_BLUE);
        canvas.drawArc(rectF1, 105, 45, false, mPaint);
        canvas.drawArc(rectF1, 390, 45, false, mPaint);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawArc(rectF2, 105, 45, false, mPaint);
        canvas.drawArc(rectF2, 390, 45, false, mPaint);
        canvas.drawArc(rectF3, 105, 45, false, mPaint);
        canvas.drawArc(rectF3, 390, 45, false, mPaint);


    }

    private void drawInnerCircle(Canvas canvas) {
//        mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(2), BlurMaskFilter.Blur.NORMAL));
        LinearGradient left = new LinearGradient(
                (float) Math.cos(90d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.sin(90d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.cos(270d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.sin(270d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                0xff2227AD, 0xff9800C4, Shader.TileMode.CLAMP);
        LinearGradient right = new LinearGradient(
                (float) Math.cos(270d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.sin(270d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.cos(450d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                (float) Math.sin(450d * Math.PI / 180d) * INNER_CIRCLE_RADIUS,
                0xff9800C4, 0xff2227AD, Shader.TileMode.CLAMP);
        mPaint.setShader(right);
        mPaint.setStrokeWidth(Utils.dp2px(30));
        mPaint.setMaskFilter(new BlurMaskFilter(Utils.dp2px(30), BlurMaskFilter.Blur.SOLID));
        RectF rectFBottom = new RectF(-INNER_CIRCLE_RADIUS, -INNER_CIRCLE_RADIUS, INNER_CIRCLE_RADIUS, INNER_CIRCLE_RADIUS);
        canvas.drawArc(rectFBottom, 90, 180, false, mPaint);
        RectF rectFTop = new RectF(-INNER_CIRCLE_RADIUS, -INNER_CIRCLE_RADIUS, INNER_CIRCLE_RADIUS, INNER_CIRCLE_RADIUS);
        mPaint.setShader(left);
        canvas.drawArc(rectFTop, 270, 180, false, mPaint);
    }

    private void drawInnerText(Canvas canvas) {
        mPaint.setTextSize(Utils.dp2px(50));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setColor(0xffffffff);
        mPaint.setShader(null);
        mPaint.setMaskFilter(null);
        mPaint.getFontMetrics(mMetrics);
        canvas.drawText(text + "", 0, -(mMetrics.ascent + mMetrics.descent) / 2, mPaint);
    }

    private void drawLine(Canvas canvas) {
        mPaint.setColor(COLOR_BLUE);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setStrokeWidth(Utils.dp2px(3));
        float x1 = (float) (Math.cos(105d * Math.PI / 180d) * MAX_RADIUS);
        float y1 = (float) (Math.sin(105d * Math.PI / 180d) * MAX_RADIUS);

        float x2 = (float) (Math.cos(105d * Math.PI / 180d) * MIDDLE_RADIUS);
        float y2 = (float) (Math.sin(105d * Math.PI / 180d) * MIDDLE_RADIUS);

        float x3 = (float) (Math.cos(105d * Math.PI / 180d) * MINI_RADIUS);
        float y3 = (float) (Math.sin(105d * Math.PI / 180d) * MINI_RADIUS);

        Path path1 = new Path();
        Path path2 = new Path();
        Path path3 = new Path();
        path1.moveTo(x1, y1);
        path2.moveTo(x2, y2);
        path3.moveTo(x3, y3);
        path1.quadTo(x1 + Utils.dp2px(15), (y1 + Utils.dp2px(10)), (x1 + Utils.dp2px(15)), (y1 + Utils.dp2px(50)));
        path2.quadTo(x2 + Utils.dp2px(15), (y2 + Utils.dp2px(10)), (x1 + Utils.dp2px(15)), (y1 + Utils.dp2px(50)));
        path3.quadTo(x3 + Utils.dp2px(15), (y3 + Utils.dp2px(10)), (x1 + Utils.dp2px(15)), (y1 + Utils.dp2px(50)));
        canvas.drawPath(path1, mPaint);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawPath(path2, mPaint);
        canvas.drawPath(path3, mPaint);

        float x4 = (float) (Math.cos(435d * Math.PI / 180d) * MAX_RADIUS);
        float y4 = (float) (Math.sin(435d * Math.PI / 180d) * MAX_RADIUS);

        float x5 = (float) (Math.cos(435d * Math.PI / 180d) * MIDDLE_RADIUS);
        float y5 = (float) (Math.sin(435d * Math.PI / 180d) * MIDDLE_RADIUS);

        float x6 = (float) (Math.cos(435d * Math.PI / 180d) * MINI_RADIUS);
        float y6 = (float) (Math.sin(435d * Math.PI / 180d) * MINI_RADIUS);

        Path path4 = new Path();
        Path path5 = new Path();
        Path path6 = new Path();
        path4.moveTo(x4, y4);
        path5.moveTo(x5, y5);
        path6.moveTo(x6, y6);
        path4.quadTo(x4 - Utils.dp2px(15), (y4 + Utils.dp2px(25)), (x4 - Utils.dp2px(15)), (y4 + Utils.dp2px(50)));
        path5.quadTo(x5 - Utils.dp2px(15), (y5 + Utils.dp2px(25)), (x4 - Utils.dp2px(15)), (y4 + Utils.dp2px(50)));
        path6.quadTo(x6 - Utils.dp2px(15), (y6 + Utils.dp2px(25)), (x4 - Utils.dp2px(15)), (y4 + Utils.dp2px(50)));
        mPaint.setStrokeWidth(Utils.dp2px(3));
        canvas.drawPath(path4, mPaint);
        mPaint.setStrokeWidth(Utils.dp2px(2));
        canvas.drawPath(path5, mPaint);
        canvas.drawPath(path6, mPaint);

        mPaint.setStrokeWidth(Utils.dp2px(3));
        canvas.drawLine(x1 + Utils.dp2px(15), y1 + Utils.dp2px(50), x1 + Utils.dp2px(15), getHeight(), mPaint);
        canvas.drawLine(x4 - Utils.dp2px(15), y4 + Utils.dp2px(50), x4 - Utils.dp2px(15), getHeight(), mPaint);

        mPaint.setStrokeWidth(2);
        canvas.drawLine(x1 + Utils.dp2px(23), INNER_CIRCLE_RADIUS, x1 + Utils.dp2px(23), getHeight(), mPaint);
        canvas.drawLine(x4 - Utils.dp2px(23), INNER_CIRCLE_RADIUS, x4 - Utils.dp2px(23), getHeight(), mPaint);
        canvas.drawLine(0, INNER_CIRCLE_RADIUS, 0, getHeight(), mPaint);


        float height = Utils.dp2px(60);

        mPaint.setColor(COLOR_PURPLE);
        mPaint.setStrokeWidth(Utils.dp2px(3));
        LinearGradient linearGradient1 = new LinearGradient(0, getHeight() / 2f - translate1, 0, +getHeight() / 2f + height - translate1, 0xff7EC8DF, 0x337EC8DF, Shader.TileMode.CLAMP);
        LinearGradient linearGradient2 = new LinearGradient(x1 + Utils.dp2px(23), getHeight() / 2f - translate2, x1 + Utils.dp2px(23), +getHeight() / 2f + height - translate2, 0xff7EC8DF, 0x337EC8DF, Shader.TileMode.CLAMP);
        LinearGradient linearGradient3 = new LinearGradient(x4 - Utils.dp2px(23), getHeight() / 2f - translate3, x4 - Utils.dp2px(23), getHeight() / 2f + height - translate3, 0xff7EC8DF, 0x337EC8DF, Shader.TileMode.CLAMP);
        mPaint.setShader(linearGradient1);
        canvas.drawLine(0, getHeight() / 2f - translate1, 0, +getHeight() / 2f + height - translate1, mPaint);
        mPaint.setShader(linearGradient2);
        canvas.drawLine(x1 + Utils.dp2px(23), getHeight() / 2f - translate2, x1 + Utils.dp2px(23), +getHeight() / 2f + height - translate2, mPaint);
        mPaint.setShader(linearGradient3);
        canvas.drawLine(x4 - Utils.dp2px(23), getHeight() / 2f - translate3, x4 - Utils.dp2px(23), getHeight() / 2f + height - translate3, mPaint);
    }


    private void drawAnimLine(Canvas canvas) {

    }

    public void startAnim() {
        ObjectAnimator animator1 = ObjectAnimator.ofFloat(this, "translate1", 0f, getHeight() / 2f - INNER_CIRCLE_RADIUS - Utils.dp2px(20));
        animator1.setDuration(800);
        animator1.setRepeatCount(-1);
        animator1.start();

        ObjectAnimator animator2 = ObjectAnimator.ofFloat(this, "translate2", 0f, getHeight() / 2f - INNER_CIRCLE_RADIUS - Utils.dp2px(20));
        animator2.setDuration(800);
        animator2.setStartDelay(300);
        animator2.setRepeatCount(-1);
        animator2.start();

        ObjectAnimator animator3 = ObjectAnimator.ofFloat(this, "translate3", 0f, getHeight() / 2f - INNER_CIRCLE_RADIUS - Utils.dp2px(20));
        animator3.setDuration(800);
        animator3.setStartDelay(600);
        animator3.setRepeatCount(-1);
        animator3.start();

        ObjectAnimator animator4 = ObjectAnimator.ofInt(this, "text", 50, 100);
        animator4.setDuration(20000);
        animator4.start();
    }

    public void setTranslate1(float translate1) {
        this.translate1 = translate1;
        invalidate();
    }

    public void setTranslate2(float translate2) {
        this.translate2 = translate2;
    }

    public void setTranslate3(float translate3) {
        this.translate3 = translate3;
    }

    public void setText(int text) {
        this.text = text;
    }
}
