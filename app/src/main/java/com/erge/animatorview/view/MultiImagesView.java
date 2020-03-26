package com.erge.animatorview.view;

import android.content.Context;
import android.content.res.TypedArray;
import android.graphics.Bitmap;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.SimpleTarget;
import com.bumptech.glide.request.transition.Transition;
import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.List;

/**
 * 九宫格图片显示
 * Created by erge 2020-03-18 16:32
 */
public class MultiImagesView extends ViewGroup implements View.OnClickListener {

    // 图片集合
    private List<String> mImgUrls;
    // 单个图片的点击事件
    private OnImageClickListener mOnImageClickListener;
    // 每个图片的间距
    private int mDividerSpace;
    // 排版模式
    private MODE mMode;
    // 图片比例
    private float mRatio = 343f / 160f;

    public MultiImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiImagesView);
        mDividerSpace = (int) typedArray.getDimension(R.styleable.MultiImagesView_dividerSpace, (int) Utils.dp2px(5));
        typedArray.recycle();
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        final int height = MeasureSpec.getSize(heightMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChildWithMargins(getChildAt(i), widthMeasureSpec, 0, heightMeasureSpec, 0);
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(height, heightMeasureSpec));
    }

    @Override
    protected void measureChildWithMargins(View child, int parentWidthMeasureSpec, int widthUsed, int parentHeightMeasureSpec, int heightUsed) {
        // 父控件宽度
        int parentWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        // 子控件宽高以及测量规则
        int itemWidth;
        int itemHeight;
        int childWidthMeasureSpec;
        int childHeightMeasureSpec;
        if (mMode == MODE.ONE_FIXED_RADIO) {
            // 单张图模式，具有一定的宽高比
            itemWidth = parentWidth;
            itemHeight = (int) (itemWidth / mRatio);
        } else if (mMode == MODE.ONE_WRAP_CONTENT) {
            // 单张图模式，宽度铺满，高度自适应
            itemWidth = parentWidth;
            itemHeight = (int) (itemWidth / mRatio);
        } else {
            // 多张图模式
            itemWidth = (parentWidth - mDividerSpace * 2) / 3;
            itemHeight = itemWidth;
        }

        childWidthMeasureSpec = getChildMeasureSpec(MeasureSpec.EXACTLY, 0, itemWidth);
        childHeightMeasureSpec = getChildMeasureSpec(MeasureSpec.EXACTLY, 0, itemHeight);
        // 测量子View
        child.measure(childWidthMeasureSpec, childHeightMeasureSpec);
    }

    @Override
    protected void onLayout(boolean changed, int l, int t, int r, int b) {
        if (mMode == MODE.ONE_FIXED_RADIO || mMode == MODE.ONE_WRAP_CONTENT) {
            layoutOne();
        } else if (mMode == MODE.FOUR) {
            layoutFour();
        } else {
            layoutMultiple();
        }
    }

    private void layoutOne() {
        final View view = getChildAt(0);
        int l = 0;
        int t = 0;
        int r = view.getMeasuredWidth();
        int b = view.getMeasuredHeight();
        view.layout(l, t, r, b);
    }

    private void layoutFour() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int itemWidth = view.getMeasuredWidth();
            int l = (i % 2) * (itemWidth + mDividerSpace);
            int t = (i / 2) * (itemWidth + mDividerSpace);
            int r = l + itemWidth;
            int b = t + itemWidth;
            view.layout(l, t, r, b);
        }
    }

    private void layoutMultiple() {
        for (int i = 0; i < getChildCount(); i++) {
            View view = getChildAt(i);
            int itemWidth = view.getMeasuredWidth();
            int l = (i % 3) * (itemWidth + mDividerSpace);
            int t = (i / 3) * (itemWidth + mDividerSpace);
            int r = l + itemWidth;
            int b = t + itemWidth;
            view.layout(l, t, r, b);
        }
    }

    public void setImages(List<String> images, OnImageClickListener clickListener) {
        setImages(images, false, clickListener);
    }

    /**
     * 设置图片
     *
     * @param images        图片集合
     * @param clickListener 事件监听
     * @param wrap          图片宽高是否适应，true：适应，false：以固定比例显示
     */
    public void setImages(List<String> images, boolean wrap, OnImageClickListener clickListener) {
        if (images != null && !images.isEmpty()) {
            mImgUrls = images;
            mOnImageClickListener = clickListener;
            final int size = mImgUrls.size();
            if (size == 1) {
                mMode = wrap ? MODE.ONE_WRAP_CONTENT : MODE.ONE_FIXED_RADIO;
            } else if (size == 4) {
                mMode = MODE.FOUR;
            } else {
                mMode = MODE.MULTIPLE;
            }
            for (int i = 0; i < images.size(); i++) {
                final ImageView imageView = new ImageView(getContext());

                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                if (mMode == MODE.ONE_WRAP_CONTENT) {
                    Glide.with(getContext()).asBitmap().load(images.get(i)).into(new SimpleTarget<Bitmap>() {
                        @Override
                        public void onResourceReady(@NonNull Bitmap resource, @Nullable Transition<? super Bitmap> transition) {
                            int width = resource.getWidth();
                            int height = resource.getHeight();
                            if (height != 0) {
                                mRatio = width * 1f / height;
                            }
                            imageView.setImageBitmap(resource);
                        }
                    });
                } else {
                    Glide.with(getContext()).load(mImgUrls.get(i)).into(imageView);
                }
                imageView.setImageResource(R.color.colorAccent);
                imageView.setOnClickListener(this);
                imageView.setTag(i);
                addView(imageView);
            }
        }
    }

    @Override
    public void onClick(View v) {
        if (mOnImageClickListener != null) {
            mOnImageClickListener.onImageClick(mImgUrls, (Integer) v.getTag());
        }
    }

    public interface OnImageClickListener {
        void onImageClick(List<String> images, int position);
    }

    public enum MODE {
        // 单张图--固定比例
        ONE_FIXED_RADIO,
        // 单张图--宽度固定，高度随图片变化
        ONE_WRAP_CONTENT,
        // 四张图
        FOUR,
        // 多张图
        MULTIPLE
    }

}
