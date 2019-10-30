package com.erge.animatorview.view;

import android.animation.ObjectAnimator;
import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.util.AttributeSet;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;


/**
 * Created by erge 2019-08-27 11:56
 */
public class LongClickView extends View {

    private Paint mPaint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private Drawable mBgDrawable;
    private Drawable mDrawable;
    private GestureDetector mDetector;
    private boolean mIsLongClick;
    private int progress = 0;

    public LongClickView(final Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        mDetector = new GestureDetector(context, new GestureDetector.SimpleOnGestureListener() {

            @Override
            public boolean onDown(MotionEvent e) {
                mIsLongClick = false;
                return true;
            }

            @Override
            public boolean onSingleTapUp(MotionEvent e) {
                Toast.makeText(context, "click", Toast.LENGTH_SHORT).show();
                return super.onSingleTapUp(e);
            }

            @Override
            public void onLongPress(MotionEvent e) {
                super.onLongPress(e);
                mIsLongClick = true;
                startAnim();
            }
        });
        initAttr(attrs);
    }

    private void initAttr(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.LongClickView);
        mBgDrawable = typedArray.getDrawable(R.styleable.LongClickView_bgSrc);
        mDrawable = typedArray.getDrawable(R.styleable.LongClickView_src);
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        canvas.translate(getWidth() >> 1, getHeight() >> 1);
        drawBgDrawable(canvas);
        drawDrawable(canvas);
        if (mIsLongClick) {
            drawCircle(canvas);
        }
    }

    private void drawBgDrawable(Canvas canvas) {
        if (mBgDrawable == null) return;
        canvas.save();
        Bitmap bitmap = ((BitmapDrawable) mBgDrawable).getBitmap();
        int size = mIsLongClick ? (int) (bitmap.getWidth() * 1.3 * 1.3) : (int) (bitmap.getWidth() * 1.3);
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
        canvas.drawBitmap(bitmap, -size >> 1, -size >> 1, mPaint);
        canvas.restore();
    }

    private void drawDrawable(Canvas canvas) {
        if (mDrawable == null) return;
        canvas.save();
        Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
        int size = mIsLongClick ? (int) (bitmap.getWidth() * 1.3) : bitmap.getWidth();
        bitmap = Bitmap.createScaledBitmap(bitmap, size, size, true);
        canvas.drawBitmap(bitmap, -size >> 1, -size >> 1, mPaint);
        canvas.restore();
    }

    private void drawCircle(Canvas canvas) {
        canvas.save();
        Bitmap bitmap = ((BitmapDrawable) mDrawable).getBitmap();
        float size = bitmap.getWidth() * 1.495f;
        mPaint.setColor(Color.parseColor("#00ff00"));
        mPaint.setStrokeWidth(5);
        mPaint.setStyle(Paint.Style.STROKE);
        canvas.drawArc(-size / 2, -size / 2, size / 2, size / 2,
                270, progress * 360f / 100, false, mPaint);
        canvas.restore();
    }

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (event.getActionMasked() == MotionEvent.ACTION_UP) {
            mIsLongClick = false;
            invalidate();
        }
        return mDetector.onTouchEvent(event);
    }

    private void startAnim() {
        ObjectAnimator objectAnimator = ObjectAnimator.ofInt(this, "progress", 0, 100);
        objectAnimator.setDuration(2000);
        objectAnimator.start();
    }

    public int getProgress() {
        return progress;
    }

    public void setProgress(int mProgress) {
        this.progress = mProgress;
        invalidate();
    }
}