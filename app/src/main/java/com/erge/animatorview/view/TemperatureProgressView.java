package com.erge.animatorview.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.LinearGradient;
import android.graphics.Paint;
import android.graphics.Shader;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.utils.Utils;

/**
 * Created by erge 2020/5/19 5:02 PM
 */
public class TemperatureProgressView extends View {

    private static final int LINE_SIZE = (int) Utils.dp2px(14);
    private static final int TEXT_SIZE = (int) Utils.dp2px(14);
    private static final int BOTTOM_LINE_COLOR = Color.parseColor("#F4F5F6");
    private static final int DEFAULT_PROGRESS_LINE_COLOR = Color.parseColor("#A3A8AF");
    private static final int CIRCLE_COLOR = Color.parseColor("#FFFFFF");
    private static final int TEXT_COLOR = Color.parseColor("#82868F");

    // 画进度条的画笔
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 画文字的画笔
    private Paint mTextPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    // 进度值，默认温度是22度
    private float mProgress = 6f / 16f;
    // 滑动回调
    private OnTemperatureChooseListener mOnTemperatureChooseListener;
    // 进度条渐变色起始和终止值
    private int mProgressStartColor = DEFAULT_PROGRESS_LINE_COLOR, mProgressEndColor = DEFAULT_PROGRESS_LINE_COLOR;
    // 类型：温度还是档次
    private Type mType = Type.TEMPERATURE_TXT;
    private Paint.FontMetrics metrics = new Paint.FontMetrics();

    public TemperatureProgressView(Context context, @Nullable AttributeSet attrs) {
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
        canvas.save();
        // 画底部灰色
        drawBottomLine(canvas);
        // 画进度
        drawProgressLine(canvas);
        // 画进度条中的圆
        drawCircle(canvas);
        // 画进度条下面的文字
        drawTemperatureText(canvas);
        canvas.restore();
    }

    private void drawTemperatureText(Canvas canvas) {
        mTextPaint.setTextSize(TEXT_SIZE);
        mTextPaint.setColor(TEXT_COLOR);
        mTextPaint.setTextAlign(Paint.Align.CENTER);
        mTextPaint.getFontMetrics(metrics);
        float baeLine = getHeight() - Utils.dp2px(2);
        canvas.drawText(mType == Type.GRADE ? "低" : "16℃", LINE_SIZE, baeLine, mTextPaint);
        canvas.drawText(mType == Type.GRADE ? "中" : "24℃", getWidth() >> 1, baeLine, mTextPaint);
        canvas.drawText(mType == Type.GRADE ? "高" : "32℃", getWidth() - LINE_SIZE, baeLine, mTextPaint);
        if (mType == Type.TEMPERATURE_TXT) {
            canvas.drawText(getTemperature() + "℃", (getWidth() - LINE_SIZE * 2) * mProgress + (LINE_SIZE), LINE_SIZE - Utils.dp2px(4), mTextPaint);
        }
    }

    private void drawCircle(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(BOTTOM_LINE_COLOR);
        float radius1 = LINE_SIZE / 2f + Utils.dp2px(2);
        float radius2 = LINE_SIZE / 2f;
        canvas.drawCircle((getWidth() - LINE_SIZE * 2) * mProgress + (LINE_SIZE), LINE_SIZE + (LINE_SIZE >> 1), radius1, mPaint);
        mPaint.setColor(CIRCLE_COLOR);
        canvas.drawCircle((getWidth() - LINE_SIZE * 2) * mProgress + (LINE_SIZE), LINE_SIZE + (LINE_SIZE >> 1), radius2, mPaint);
    }

    private void drawProgressLine(Canvas canvas) {
        final LinearGradient gradient = new LinearGradient(LINE_SIZE, LINE_SIZE + (LINE_SIZE >> 1),
                (getWidth() - LINE_SIZE * 2) * mProgress, LINE_SIZE + (LINE_SIZE >> 1),
                mProgressStartColor, mProgressEndColor, Shader.TileMode.CLAMP);
        mPaint.setShader(gradient);
        mPaint.setStrokeWidth(LINE_SIZE);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        canvas.drawLine(LINE_SIZE, LINE_SIZE + (LINE_SIZE >> 1),
                (getWidth() - LINE_SIZE * 2) * mProgress + LINE_SIZE, LINE_SIZE + (LINE_SIZE >> 1), mPaint);
    }

    private void drawBottomLine(Canvas canvas) {
        mPaint.setShader(null);
        mPaint.setColor(BOTTOM_LINE_COLOR);
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setStrokeCap(Paint.Cap.ROUND);
        mPaint.setStrokeWidth(LINE_SIZE);
        canvas.drawLine(LINE_SIZE, LINE_SIZE + (LINE_SIZE >> 1),
                getWidth() - LINE_SIZE, LINE_SIZE + (LINE_SIZE >> 1), mPaint);
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        int totalLineWidth = getWidth();
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
            case MotionEvent.ACTION_MOVE:
                mProgress = (event.getX()) * 1f / totalLineWidth;
                if (mProgress < 0) mProgress = 0;
                if (mProgress > 1) mProgress = 1;
                // 如果是档次类型，则进度条只能是0，0.5和1三个进度值
                if (mType == Type.GRADE) {
                    if (mProgress < 0.25f) {
                        mProgress = 0f;
                    } else if (mProgress > 0.25f && mProgress < 0.75f) {
                        mProgress = 0.5f;
                    } else {
                        mProgress = 1f;
                    }
                }
                if (mOnTemperatureChooseListener != null) {

                    mOnTemperatureChooseListener.onTemperatureChoose(getTemperature());
                }
                break;
        }
        invalidate();
        return true;
    }

    private String getTemperature() {
        int temperature = (int) (16 * (mProgress + 1));
        return String.valueOf(temperature);
    }

    public void setProgressColor(int startColor, int endColor) {
        mProgressStartColor = startColor;
        mProgressEndColor = endColor;
        invalidate();
    }

    public void setOnTemperatureChooseListener(OnTemperatureChooseListener onTemperatureChooseListener) {
        this.mOnTemperatureChooseListener = onTemperatureChooseListener;
    }

    public interface OnTemperatureChooseListener {
        void onTemperatureChoose(String temperature);
    }

    public enum Type {
        // 进度条下方显示具体温度
        TEMPERATURE_TXT,
        // 进度条下方显示档次：低中高
        GRADE
    }

}
