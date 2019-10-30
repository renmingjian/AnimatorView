package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.ViewConfiguration;
import android.view.animation.LinearInterpolator;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.ArrayList;
import java.util.List;

/**
 * 字母列表
 * Created by erge 2019-10-05 10:59
 */
public class CharacterView extends View {

    private static final int TEXT_SIZE = (int) Utils.dp2px(12);
    private static final int MARGIN = (int) Utils.dp2px(5);
    private static final int VIEW_HEIGHT = TEXT_SIZE * 26 + MARGIN * 26 * 2;
    private static final int VIEW_WIDTH = (int) Utils.dp2px(20);

    private String[] mCharacters;
    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Paint.FontMetrics mMetrics = new Paint.FontMetrics();
    // 存放每个字母基线位置的集合
    private List<Float> mY = new ArrayList<>();
    // 是否点击某个字母
    private boolean mClick;
    // 点击该字母后，该字母在数组中的位置
    private int mCurrentClickIndex = -1;
    // 点击监听
    private OnCharacterClickListener mOnCharacterClickListener;
    // 按下的坐标
    private float mDownX, mDownY;
    // 按下时的时间
    private long mDownTime;
    // 点击后，在该字母上覆盖的红色背景圆的半径
    private float bgRadius;

    public CharacterView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mPaint.setColor(Color.parseColor("#8c8c8c"));
        mPaint.setTextSize(TEXT_SIZE);
        mPaint.getFontMetrics(mMetrics);
        mPaint.setTextAlign(Paint.Align.CENTER);
        mCharacters = getResources().getStringArray(R.array.characters);
        for (int i = 0; i < mCharacters.length; i++) {
            mY.add(i * TEXT_SIZE + 0.5f * TEXT_SIZE + MARGIN + 2 * i * MARGIN - (mMetrics.ascent + mMetrics.descent) / 2);
        }
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        setMeasuredDimension(resolveSize(VIEW_WIDTH, widthMeasureSpec), resolveSize(VIEW_HEIGHT, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        canvas.save();
        for (int i = 0; i < mCharacters.length; i++) {
            mPaint.setColor(Color.parseColor("#8c8c8c"));
            canvas.drawText(mCharacters[i], VIEW_WIDTH >> 1, mY.get(i), mPaint);
            if (mClick && mCurrentClickIndex != -1) {
                mPaint.setColor(Color.parseColor("#11FF0000"));
                canvas.drawCircle(VIEW_WIDTH * 0.5f, mY.get(mCurrentClickIndex) - Utils.dp2px(5), bgRadius, mPaint);
            }
        }
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        boolean handle = false;
        switch (event.getActionMasked()) {
            case MotionEvent.ACTION_DOWN:
                handle = true;
                mDownX = event.getX();
                mDownY = event.getY();
                mDownTime = System.currentTimeMillis();
                break;
            case MotionEvent.ACTION_UP:
                long upTime = System.currentTimeMillis();
                float upX = event.getX();
                float upY = event.getY();
                float instanceX = Math.abs(mDownX - upX);
                float instanceY = Math.abs(mDownY - upY);
                // 如果滑动时间小于400毫秒并且距离小于系统认为的点击事件的距离，则认为该次触摸事件为点击事件
                if ((upTime - mDownTime) <= 400 && Math.sqrt(instanceX * instanceX + instanceY * instanceY) <= ViewConfiguration.get(getContext()).getScaledTouchSlop()) {
                    mClick = true;
                    // 找该次点击是点击的哪个字母的位置
                    for (int i = 0; i < mY.size(); i++) {
                        if (upY >= mY.get(i) - Utils.dp2px(11) && upY <= mY.get(i) + Utils.dp2px(11)) {
                            mCurrentClickIndex = i;
                            break;
                        }
                    }
                    if (mOnCharacterClickListener != null && mCurrentClickIndex != -1) {
                        mOnCharacterClickListener.onCharacterClick(mCharacters[mCurrentClickIndex], mCurrentClickIndex);
                    }
                    // 点击后开启动画
                    bgAnim();
                }
                break;
        }
        return handle;
    }

    private void bgAnim() {
        final float radius = Utils.dp2px(10);
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "bgRadius", radius * 0.5f, radius);
        animator.setDuration(300);
        animator.setInterpolator(new LinearInterpolator());
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value == radius) {
                    mClick = false;
                    mCurrentClickIndex = -1;
                }
            }
        });
        animator.start();
    }

    public void setBgRadius(float bgRadius) {
        this.bgRadius = bgRadius;
        invalidate();
    }

    public void setOnCharacterClickListener(OnCharacterClickListener onCharacterClickListener) {
        this.mOnCharacterClickListener = onCharacterClickListener;
    }

    public interface OnCharacterClickListener {
        void onCharacterClick(String character, int position);
    }
}
