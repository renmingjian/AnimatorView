package com.erge.animatorview.view;
import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import java.util.List;
/**
 * 九宫格图⽚显示
 * Created by erge 2020-03-18 16:32
 */
public class MultiImagesView extends ViewGroup implements View.OnClickListener {
    // 图⽚集合
    private List<String> mImgUrls;
    // 单个图⽚的点击事件
    private OnImageClickListener mOnImageClickListener;
    // 每个图⽚的间距
    private int mDividerSpace;
    // 排版模式
    private MODE mMode;
    // 图⽚⽐例
    private float mRatio = 343f / 160f;
    // ⽗容器⾼度
    private int mParentHeight;
    public MultiImagesView(Context context, AttributeSet attrs) {
        super(context, attrs);
        TypedArray typedArray = context.obtainStyledAttributes(attrs, R.styleable.MultiImagesView);
        mDividerSpace = (int) typedArray.getDimension(R.styleable.MultiImagesView_dividerSpace, Utils.dp2px(5));
        typedArray.recycle();
    }
    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int width = MeasureSpec.getSize(widthMeasureSpec);
        int childCount = getChildCount();
        for (int i = 0; i < childCount; i++) {
            measureChild(getChildAt(i), widthMeasureSpec, heightMeasureSpec);
        }
        setMeasuredDimension(resolveSize(width, widthMeasureSpec), resolveSize(mParentHeight, heightMeasureSpec));
    }
    @Override
    protected void measureChild(View child, int parentWidthMeasureSpec, int parentHeightMeasureSpec) {
        // ⽗控件宽度
        int parentWidth = MeasureSpec.getSize(parentWidthMeasureSpec);
        // ⼦控件宽⾼以及测量规则
        int childWidth;
        int childHeight;
        if (mMode == MODE.ONE_FIXED_RADIO) {
            // 单张图模式，具有固定的宽⾼⽐
            childWidth = parentWidth;
            childHeight = (int) (childWidth / mRatio);
            mParentHeight = childHeight;
        } else if (mMode == MODE.ONE_WRAP_CONTENT) {
            // 单张图模式，宽度铺满，⾼度⾃适应
            childWidth = parentWidth;
            childHeight = (int) (childWidth / mRatio);
            mParentHeight = childHeight;
        } else {
            // 多张图模式
            int imageSize = (parentWidth - mDividerSpace * 2) / 3;
            childWidth = imageSize;
            childHeight = imageSize;
            if (mMode == MODE.FOUR) {
                mParentHeight = childHeight * 2 + mDividerSpace;
            } else {
                int count = getChildCount();
                int a = count % 3 == 0 ? count / 3 : count / 3 + 1;
                mParentHeight = a * childWidth + (a - 1) * mDividerSpace;
            }
        }
        int childWidthMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, 0, childWidth);
        int childHeightMeasureSpec = getChildMeasureSpec(parentWidthMeasureSpec, 0, childHeight);
        // 测量⼦View
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
    @Override
    protected LayoutParams generateDefaultLayoutParams() {
        return new MarginLayoutParams(LayoutParams.WRAP_CONTENT, LayoutParams.WRAP_CONTENT);
    }
    @Override
    public boolean shouldDelayChildPressedState() {
        return false;
    }
    @Override
    protected boolean checkLayoutParams(LayoutParams p) {
        return p instanceof MarginLayoutParams;
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
    /**
     * 设置图⽚
     *
     * @param images 图⽚集合
     * @param clickListener 事件监听
     * @param wrap 图⽚宽⾼是否适应，true：适应，false：以固定⽐例显示
     */
    public void setImages(List<String> images, boolean wrap, OnImageClickListener clickListener) {
        if (images != null && !images.isEmpty()) {
            if (getChildCount() > 0) removeAllViews();
            mImgUrls = images;
            mOnImageClickListener = clickListener;
            final int size = mImgUrls.size();
            mMode = getMode(size, wrap);
            for (int i = 0; i < images.size(); i++) {
                final ImageView imageView = new ImageView(getContext());
                final String imageUrl = images.get(i);
                imageView.setScaleType(ImageView.ScaleType.CENTER_CROP);
                loadImage(imageUrl, imageView);
                if (clickListener != null) {
                    imageView.setOnClickListener(this);
                }
                imageView.setTag(i);
                addView(imageView);
            }
        }
    }
    /**
     * 根据图⽚数量和是否⾃适应来获取图⽚模式
     *
     * @param imageSize 图⽚数量
     * @param wrap 是否需要⾃适应
     * @return 模式
     */
    private MODE getMode(int imageSize, boolean wrap) {
        final MODE mode;
        if (imageSize == 1) {
            mode = wrap ? MODE.ONE_WRAP_CONTENT : MODE.ONE_FIXED_RADIO;
        } else if (imageSize == 4) {
            mode = MODE.FOUR;
        } else {
            mode = MODE.MULTIPLE;
        }
        return mode;
    }
    /**
     * 加载图⽚
     * ⾃适应图⽚需要先获取到图⽚的⼤⼩，从⽽确定⾃⼰的⼤⼩然后确定⽗控件的⼤⼩
     *
     * @param imageUrl 图⽚URL
     * @param imageView 控件
     */
    private void loadImage(String imageUrl, ImageView imageView) {
//        try {
//            imageView.setImageResource(R.mipmap.iv_loading_round);
//            Glide.with(getContext())
//                    .load(imageUrl)
//                    .placeholder(R.mipmap.iv_loading_round)
//                    .error(R.mipmap.img_error)
//                    .into(new SimpleTarget<GlideDrawable>() {
//                        @Override
//                        public void onResourceReady(GlideDrawable resource, GlideAnimation<? super GlideDrawable> glideAnimation) {
//                            // ⾃适应图⽚，需要获取宽⾼并确定宽⾼⽐
//                            if (mMode == MODE.ONE_WRAP_CONTENT) {
//                                int width = resource.getIntrinsicWidth();
//                                int height = resource.getIntrinsicHeight();
//                                if (height != 0) {
//                                    mRatio = width * 1f / height;
//                                }
//                            }
//                            // 设置图⽚
//                            imageView.setImageDrawable(resource);
//                            // 如果随是GIF图，则开启多张图⽚的播放
//                            if (resource instanceof GifDrawable) {
//                                GifDrawable gifDrawable = (GifDrawable) resource;
//                                gifDrawable.start();
//                            }
//                        }
//                    });
//        } catch (Exception e) {
//            LogUtil.e("MultiImagesView--->loadImage--->" + e.getMessage());
//        }
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
        // 单张图--固定⽐例
        ONE_FIXED_RADIO,
        // 单张图--宽度固定，⾼度随图⽚变化
        ONE_WRAP_CONTENT,
        // 四张图
        FOUR,
        // 多张图
        MULTIPLE
    }
}