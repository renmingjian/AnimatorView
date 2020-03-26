package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.util.AttributeSet;

import androidx.annotation.Nullable;
import androidx.appcompat.widget.AppCompatImageView;

/**
 * Created by erge 2020-01-03 16:28
 */
public class BoomView extends AppCompatImageView {

    private boolean boom = false;
    private float progress = 0f;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);

    public BoomView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    @Override
    protected void onDraw(Canvas canvas) {
        if (boom) {
            drawBoom(canvas);
        } else {
            super.onDraw(canvas);
        }
    }

    private void drawBoom(Canvas canvas) {
        canvas.save();
        canvas.restore();
    }

    public void boom() {
        boom = true;
        anim();
    }

    private void anim() {
        ObjectAnimator animator = ObjectAnimator.ofFloat(this, "progress", 0f, 1f);
        animator.setDuration(200);
        animator.start();
    }

    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }
}
