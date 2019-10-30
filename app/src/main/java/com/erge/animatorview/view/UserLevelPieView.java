package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.graphics.RectF;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;


import com.erge.animatorview.utils.Utils;

import java.util.List;

/**
 * Created by erge 2019-09-11 15:12
 */
public class UserLevelPieView extends View {

    private int mWidth;
    private int mHeight;
    private Paint mPaint;
    private List<LevelPieInfo> mData;
    private int mAllCount;
    private float mStartAngle = 0f;
    private int mRingStrokeWidth = (int) Utils.dp2px(30);
    private float mRadius;
    // 环形动画进度
    private float ringProgress;

    public UserLevelPieView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onSizeChanged(int w, int h, int oldw, int oldh) {
        super.onSizeChanged(w, h, oldw, oldh);
        mWidth = getWidth();
        mHeight = getHeight();
        mRadius = mHeight / 4f;
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.translate(mWidth >> 1, mHeight >> 1);
        if (mData == null || mData.size() == 0) {
            drawEmpty(canvas);
            drawInnerText(canvas);
            return;
        }
        for (LevelPieInfo info : mData) {
            draw(canvas, info);
        }
    }

    private void draw(Canvas canvas, LevelPieInfo info) {
        canvas.save();

        drawRing(canvas, info);
        Point lineEndPoint = drawLine(canvas, info);
        drawDescriptionText(canvas, info, lineEndPoint);
        drawInnerText(canvas);

        canvas.restore();
    }

    /**
     * 如果没有数据，则画一个空的圆环
     */
    private void drawEmpty(Canvas canvas) {
        canvas.save();
        mPaint.setStrokeWidth(mRingStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(0xff8c8c8c);
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(rectF, 0, 360 * ringProgress, false, mPaint);

        canvas.restore();
    }

    /**
     * 画等级文字和数量比例描述文字
     */
    private void drawDescriptionText(Canvas canvas, LevelPieInfo info, Point lineEndPoint) {
        mPaint.setColor(Color.parseColor("#666666"));
        mPaint.setStyle(Paint.Style.FILL);
        mPaint.setTextAlign(lineEndPoint.x >= 0 ? Paint.Align.LEFT : Paint.Align.RIGHT);
        mPaint.setTextSize(Utils.dp2px(12));
        final String levelString = info.getLevel();
        final int fraction = info.getCount() * 100 / mAllCount;
        final String fractionString = info.getCount() + " , " + fraction + "%";

        // 确定2个文字基线
        float levelBaseLine = lineEndPoint.y + Utils.dp2px(12 >> 1);
        float fractionBaseLine = lineEndPoint.y + Utils.dp2px(12) * 3 / 2 + Utils.dp2px(5);

        // 测量2行文字长度
        float levelTextWidth = mPaint.measureText(levelString);
        float fractionTextWidth = mPaint.measureText(fractionString);

        // 文字起点位置距离屏幕两端距离
        int abs = Math.abs(Math.abs(lineEndPoint.x) - mWidth / 2);
        // 定义文字起始点位置
        int levelTextLeft = lineEndPoint.x;
        int fractionTextLeft = lineEndPoint.x;

        // 如果文字长度比abs大，说明文字会绘制在屏幕外，此时修正绘制文字的起点位置
        if (abs < levelTextWidth) {
            if (lineEndPoint.x > 0) {
                levelTextLeft = (int) (lineEndPoint.x - (levelTextWidth - abs));
            } else {
                levelTextLeft = (int) (lineEndPoint.x + (levelTextWidth - abs));
            }
        }

        if (abs < fractionTextWidth) {
            if (lineEndPoint.x > 0) {
                fractionTextLeft = (int) (lineEndPoint.x - (fractionTextWidth - abs));
            } else {
                fractionTextLeft = (int) (lineEndPoint.x + (fractionTextWidth - abs));
            }
        }

        canvas.drawText(levelString, levelTextLeft, levelBaseLine, mPaint);
        mPaint.setTextAlign(lineEndPoint.x >= 0 ? Paint.Align.LEFT : Paint.Align.RIGHT);
        mPaint.setColor(Color.parseColor("#ffd700"));
        canvas.drawText(fractionString, fractionTextLeft, fractionBaseLine, mPaint);
    }

    /**
     * 画圆圈内的文字
     */
    private void drawInnerText(Canvas canvas) {

        boolean haveData = mData != null && mData.size() > 0;

        Paint.FontMetrics metrics = new Paint.FontMetrics();
        mPaint.getFontMetrics(metrics);
        // 画中间总人数文字
//        float centerTextBaseLine = -(metrics.ascent + metrics.descent) / 2;
        float centerTextBaseLine = Utils.dp2px(10);
        mPaint.setColor(Color.parseColor("#ffd700"));
        mPaint.setTextSize(Utils.dp2px(20));
        mPaint.setTextAlign(Paint.Align.CENTER);
        mPaint.setStyle(Paint.Style.FILL);
        if (haveData) {
            canvas.drawText(mAllCount + "", 0, centerTextBaseLine, mPaint);
        } else {
            mPaint.setColor(0xff404040);
            canvas.drawText("暂无客户", 0, centerTextBaseLine, mPaint);
            return;
        }

        // 画上方客户文字（上方文字距离中间文字5dp，所以减去差值dp2px(25)）
        float topTextBaseLine = centerTextBaseLine - Utils.dp2px(25);
        mPaint.setColor(0xff404040);
        mPaint.setTextSize(Utils.dp2px(14));
        canvas.drawText("本店客户", 0, topTextBaseLine, mPaint);

        // 画下方客户文字（下方文字距离中间文字10dp，所以减去差值dp2px(30)）
        mPaint.setColor(0xff8c8c8c);
        float bottomTextBaseLine = centerTextBaseLine + Utils.dp2px(30);
        canvas.drawText("100%", 0, bottomTextBaseLine, mPaint);
    }

    /**
     * 画线
     *
     * @return 终点线的坐标
     */
    private Point drawLine(Canvas canvas, LevelPieInfo info) {
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor("#7e7e7e"));
        mPaint.setStrokeWidth(Utils.dp2px(1.5f));
        float middleAngle = info.getMiddleAnge();
        float angle = (float) (middleAngle * Math.PI / 180);
        int x = (int) ((mRadius) * Math.cos(angle));
        int y = (int) ((mRadius) * Math.sin(angle));
        int inflectionX = (int) ((mRadius + mRingStrokeWidth) * Math.cos(angle));
        int inflectionY = (int) ((mRadius + mRingStrokeWidth) * Math.sin(angle));
        int endX = (int) (inflectionX > 0 ? inflectionX + Utils.dp2px(25) : inflectionX - Utils.dp2px(25));
        int endY = inflectionY;
        // 如果起点到拐点本身是水平的，则修正终点坐标
        if (inflectionY == 0) {
            endX = inflectionX;
            endY = (int) Utils.dp2px(20);
        }
        mPaint.setStyle(Paint.Style.FILL);
        canvas.drawCircle(x, y, Utils.dp2px(3), mPaint);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawLine(x, y, inflectionX, inflectionY, mPaint);
        canvas.drawLine(inflectionX, inflectionY, endX, endY, mPaint);
        return new Point(endX, endY);
    }

    /**
     * 画圆环
     */
    private void drawRing(Canvas canvas, LevelPieInfo info) {
        mPaint.setStrokeWidth(mRingStrokeWidth);
        mPaint.setStyle(Paint.Style.STROKE);
        mPaint.setColor(Color.parseColor(info.getColor()));
        float swipeAngle = info.getCount() * 360f / mAllCount;
        RectF rectF = new RectF(-mRadius, -mRadius, mRadius, mRadius);
        canvas.drawArc(rectF, mStartAngle, swipeAngle * ringProgress, false, mPaint);
        info.setMiddleAnge(mStartAngle + swipeAngle / 2);
        mStartAngle += swipeAngle;
    }

    /**
     * 数据源
     */
    public void setData(List<LevelPieInfo> list) {
        mAllCount = 0;
        mData = list;
        for (LevelPieInfo info : mData) {
            mAllCount += info.getCount();
        }
        ringAnim();
//        invalidate();
    }

    public void setRingProgress(float ringProgress) {
        this.ringProgress = ringProgress;
        invalidate();
    }

    private void ringAnim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "ringProgress", 0f, 1f);
        animator.setDuration(1000);
        animator.start();
    }

    public static class LevelPieInfo {

        private String level;
        private int count;
        private String color;
        private float middleAnge;

        public String getLevel() {
            return level;
        }

        public void setLevel(String level) {
            this.level = level;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        public String getColor() {
            return color;
        }

        public void setColor(String color) {
            this.color = color;
        }

        public float getMiddleAnge() {
            return middleAnge;
        }

        public void setMiddleAnge(float middleAnge) {
            this.middleAnge = middleAnge;
        }
    }

}
