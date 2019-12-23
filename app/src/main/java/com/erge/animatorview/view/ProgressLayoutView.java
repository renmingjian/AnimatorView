package com.erge.animatorview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

/**
 * Created by erge 2019-12-23 15:54
 */
public class ProgressLayoutView extends View {

    private String desc;
    private float imgHeight;
    private float imgWidth;
    private BitmapDrawable drawable;
    private Paint paint = new Paint(Paint.ANTI_ALIAS_FLAG);
    private float leftMargin = Utils.dp2px(20);
    private float textSize = Utils.dp2px(20);
    private float testLeftMargin = Utils.dp2px(20);

    private float progress;

    public ProgressLayoutView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        initAttrs(attrs);
    }

    private void initAttrs(AttributeSet attrs) {
        final TypedArray typedArray = getContext().obtainStyledAttributes(attrs, R.styleable.ProgressLayoutView);
        drawable = (BitmapDrawable) typedArray.getDrawable(R.styleable.ProgressLayoutView_img);
        imgWidth = typedArray.getDimension(R.styleable.ProgressLayoutView_imgWidth, (int) Utils.dp2px(20));
        imgHeight = typedArray.getDimension(R.styleable.ProgressLayoutView_imgHeight, (int) Utils.dp2px(20));
        desc = typedArray.getString(R.styleable.ProgressLayoutView_desc);
        typedArray.recycle();
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
        canvas.translate(0, getHeight() / 2);
        drawGrayBackground(canvas);
        drawWhiteBackground(canvas);
        drawImg(canvas);
        drawText(canvas);
        canvas.restore();
    }

    private void drawGrayBackground(Canvas canvas) {
        paint.setColor(Color.parseColor("#d7d7d7"));
        canvas.drawRect(0, -getHeight() / 2, getWidth(), getHeight() / 2, paint);
    }

    private void drawWhiteBackground(Canvas canvas) {
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawRect(0, -getHeight() / 2, getWidth() * progress, getHeight() / 2, paint);
    }

    private void drawImg(Canvas canvas) {
        canvas.drawBitmap(getBitmapByDrawable(), leftMargin, -imgHeight / 2, paint);
    }

    private void drawText(Canvas canvas) {
        paint.setTextSize(textSize);
        paint.setStyle(Paint.Style.FILL);
        float start = leftMargin + imgWidth + testLeftMargin;
        float current = getWidth() * progress;

        paint.setColor(Color.parseColor("#ff0000"));
        canvas.drawLine(current, -getHeight() / 2, current + 2, getHeight() / 2, paint);


        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawText(desc, start, textSize / 2, paint);
        if (current >= start) {
            canvas.save();
            paint.setColor(Color.parseColor("#000000"));
            final Rect boundsLeft = new Rect((int) start,  -getHeight() / 2, (int) current, getHeight() / 2);
            canvas.clipRect(boundsLeft);
            paint.getTextBounds(desc, 0, desc.length(), boundsLeft);
            canvas.drawText(desc, start, textSize / 2, paint);
            canvas.restore();
        }
    }

    private Bitmap getBitmapByDrawable() {
        Bitmap bitmap = Bitmap.createBitmap((int) imgWidth, (int) imgHeight, Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);
        return bitmap;
    }

    @SuppressWarnings("unused")
    public void setProgress(float progress) {
        this.progress = progress;
        invalidate();
    }

}
