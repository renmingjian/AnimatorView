package com.erge.animatorview.view;

import android.content.Context;
import android.util.AttributeSet;
import android.view.View;

import androidx.annotation.Nullable;

/**
 * 自定义View的基类
 * 如果是自定义ViewGroup，则不能实现该类
 * 唯一的作用：对View在不同的测量模式下，实现对View的测量。最主要的是对warp_content属性做了单独处理，如果不是
 * wrap_content属性，则直接使用View自己的测量即可，如果是wrap_content，则自己处理。
 * <p>
 * 需要说明的是:
 * 1.如果有自定义View在使用时即使指定了具体的dp值还是match_parent都不起作用，而是完全靠自己的逻辑来确定View的
 * 大小，则不能继承自该View，需要自己继承View然后重写{@link #onMeasure(int, int)}方法，在该方法中调用系统的
 * {@link #setMeasuredDimension(int, int)}方法，方法中的两个参数就是自己定义的宽高值。
 * <p>
 * 2.如果继承了该类，并且还不想使dp和match_parent属性生效，则在xml布局中使用该View时宽高属性只能指定为
 * wrap_content
 * Created by mj on 2020/8/19 17:19
 */
public abstract class BaseCustomerView extends View {

    public BaseCustomerView(Context context) {
        super(context);
    }

    public BaseCustomerView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
    }

    public BaseCustomerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    public BaseCustomerView(Context context, @Nullable AttributeSet attrs, int defStyleAttr, int defStyleRes) {
        super(context, attrs, defStyleAttr, defStyleRes);
    }

    @Override
    protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
        final int widthSpecSize = MeasureSpec.getSize(widthMeasureSpec);
        final int widthSpecMode = MeasureSpec.getMode(widthMeasureSpec);
        final int heightSpecSize = MeasureSpec.getSize(heightMeasureSpec);
        final int heightSpecMode = MeasureSpec.getMode(heightMeasureSpec);
        // 宽高属性中都不是AT_MOST，则直接使用View的测量逻辑即可，此时是dp就走dp，是match_parent就填满父布局
        if (widthSpecMode != MeasureSpec.AT_MOST && heightSpecMode != MeasureSpec.AT_MOST) {
            super.onMeasure(widthMeasureSpec, heightMeasureSpec);
        } else { // 宽高中只要有属性是wrap_content，即测量模式为AT_MOST，则自己指定宽高具体为多少。
            int wideSize = widthSpecSize;
            int highSize = heightSpecSize;
            if (widthSpecMode == MeasureSpec.AT_MOST) {
                wideSize = getCustomerViewWidth();
            }
            if (heightSpecMode == MeasureSpec.AT_MOST) {
                highSize = getCustomerViewHeight();
            }
            setMeasuredDimension(wideSize, highSize);
        }
    }

    /**
     * 获取View的宽度
     * 在使用自定义View时，如果使用的是wrap_content属性，则具体的值需要自定义View自己来确定
     * 子类一般需要实现该方法，否则View的大小会默认为0
     *
     * @return View最终需要的宽度
     */
    protected int getCustomerViewWidth() {
        return 0;
    }

    /**
     * 获取View的高度
     * 在使用自定义View时，如果使用的是wrap_content属性，则具体的值需要自定义View自己来确定
     * 子类一般需要实现该方法，否则View的大小会默认为0
     *
     * @return View最终需要的高度
     */
    protected int getCustomerViewHeight() {
        return 0;
    }
}
