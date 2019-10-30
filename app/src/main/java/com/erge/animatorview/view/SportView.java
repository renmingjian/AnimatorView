package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

/**
 * Created by erge 2019-07-12 16:54
 */
public class SportView extends View {

    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private static final float RADIUS = Utils.px2dp(150);
    private static final float RING_WIDTH = Utils.dp2px(20);
    private static final int CIRCLE_COLOR = Color.parseColor("#90A4AE");
    private static final int HIGHLIGHT_COLOR = Color.parseColor("#FF4081");
    private float progress = 0f;
    private String text = "0步";
    private Paint.FontMetrics metrics = new Paint.FontMetrics();

    {
        paint.setTextSize(Utils.px2dp(60));
        // 设置字体居中对齐，缺省是居左对齐
        paint.setTextAlign(Paint.Align.CENTER);
    }

    public SportView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);

    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);

        // 绘制圆环
        paint.setColor(CIRCLE_COLOR);
        paint.setStyle(Paint.Style.STROKE);
        paint.setStrokeWidth(RING_WIDTH);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, RADIUS, paint);

        // 画进度
        paint.setColor(HIGHLIGHT_COLOR);
        paint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawArc((getWidth() >> 1) - RADIUS, (getHeight() >> 1) - RADIUS,
                (getWidth() >> 1) + RADIUS, (getHeight() >> 1) + RADIUS,
                270, progress * 360f, false, paint);
        paint.setStrokeCap(Paint.Cap.BUTT);

        // 在圆环中心绘制文字
        paint.setStyle(Paint.Style.FILL);
        paint.getFontMetrics(metrics);
        float offset = (metrics.ascent + metrics.descent) / 2;
        canvas.drawText(text, getWidth() >> 1, (getHeight() >> 1) - offset, paint);

    }

    public void setProgress(float progress) {
        if (progress < 0.0f) progress = 0.0f;
        if (progress > 1.0f) progress = 1.0f;
        this.progress = progress;
        invalidate();
    }

    public void setText(String text) {
        this.text = text;
    }

}
