package com.erge.animatorview.view;

import android.animation.AnimatorSet;
import android.animation.ValueAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;


/**
 * Created by erge 2019-07-19 17:12
 */
public class LoadingView extends View {

    private static final float MAX_RADIUS = Utils.dp2px(8);
    private static final float PADDING = Utils.dp2px(2);

    private final Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float f1;
    private float f2;
    private float f3;
    private float radius = MAX_RADIUS;
    private int color = Color.parseColor("#000000");
    private AnimatorSet animatorSet;


    public LoadingView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init(context, attrs);
        startAnim();
    }

    private void init(Context context, AttributeSet attrs) {
        final TypedArray array = context.obtainStyledAttributes(attrs, R.styleable.LoadingView);
        radius = array.getFloat(R.styleable.LoadingView_dotRadius, radius);
        color = array.getColor(R.styleable.LoadingView_dotColor, color);
        paint.setColor(color);
        array.recycle();
    }

    private void startAnim() {
        animatorSet = new AnimatorSet();
        ValueAnimator valueAnimator = ValueAnimator.ofFloat(0f, 1f, 0f);
        valueAnimator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                f1 = (float) animation.getAnimatedValue();
                invalidate();
            }
        });
        valueAnimator.setRepeatCount(2000);

        ValueAnimator valueAnimator2 = ValueAnimator.ofFloat(0f, 1f, 0f);
        valueAnimator2.setStartDelay(300);
        valueAnimator2.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                f2 = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator2.setRepeatCount(2000);

        ValueAnimator valueAnimator3 = ValueAnimator.ofFloat(0f, 1f, 0f);
        valueAnimator3.setStartDelay(600);
        valueAnimator3.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                f3 = (float) animation.getAnimatedValue();
            }
        });
        valueAnimator3.setRepeatCount(2000);

        animatorSet.playTogether(valueAnimator, valueAnimator2, valueAnimator3);
        animatorSet.setDuration(1500);
        animatorSet.start();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        // 该自定义View想要的最终的大小
        final int destWidth = (int) (getPaddingLeft() + getPaddingRight() + radius * 6 + PADDING * 2);
        final int destHeight = (int) (getPaddingTop() + getPaddingBottom() + radius * 2);
        // 通过系统提供的api确定最终大小是多少
        final int widthSize = resolveSize(destWidth, widthMeasureSpec);
        final int heightSize = resolveSize(destHeight, heightMeasureSpec);
        setMeasuredDimension(widthSize, heightSize);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.save();
        canvas.drawCircle((getWidth() >> 1) - radius * 2 - PADDING, getHeight() >> 1, radius * f1, paint);
        canvas.drawCircle(getWidth() >> 1, getHeight() >> 1, radius * f2, paint);
        canvas.drawCircle((getWidth() >> 1) + radius * 2 + PADDING, getHeight() >> 1, radius * f3, paint);
        canvas.restore();
    }

    @Override
    public void onVisibilityAggregated(boolean isVisible) {
        super.onVisibilityAggregated(isVisible);
        if (isVisible) {
            animatorSet.resume();
        } else {
            animatorSet.pause();
        }
    }

    public void setColor(int color) {
        this.color = color;
        paint.setColor(color);
    }


    public void setMaxRadius(float radius) {
        this.radius = radius;
    }

}
