package com.erge.animatorview.view;

import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Context;
import android.graphics.Rect;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.widget.FrameLayout;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;

import me.samlss.bloom.Bloom;
import me.samlss.bloom.listener.BloomListener;

/**
 * Created by erge 2020-01-03 11:43
 */
public class ZanLayout extends FrameLayout {

    private ImageView iv_1;
    private ImageView iv_2;
    private ImageView iv_3;
    private ImageView iv_4;
    private ImageView iv_5;

    public ZanLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_zan, this);
        iv_1 = view.findViewById(R.id.iv_1);
        iv_2 = view.findViewById(R.id.iv_2);
        iv_3 = view.findViewById(R.id.iv_3);
        iv_4 = view.findViewById(R.id.iv_4);
        iv_5 = view.findViewById(R.id.iv_5);
    }

    public void zan(View view) {

        final int[] location = new int[2];
        view.getLocationInWindow(location);

        final float startX = location[0];
        final float startY = location[1] + getBarHeight();

        final float radius = Utils.dp2px(150);

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1.1f, 1f);
        animator.setDuration(1000);
        animator.start();
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                if (value == 1f) {
                    boom(iv_1);
                    boom(iv_2);
                    boom(iv_3);
                    boom(iv_4);
                    boom(iv_5);
                }
                if (value == 0f) {
                    iv_1.setVisibility(View.VISIBLE);
                    iv_2.setVisibility(View.VISIBLE);
                    iv_3.setVisibility(View.VISIBLE);
                    iv_4.setVisibility(View.VISIBLE);
                    iv_5.setVisibility(View.VISIBLE);
                }
                iv_1.setX((float) ((radius * Math.cos(Math.PI * 135 / 180))) * value + startX);
                iv_1.setY((float) ((radius * Math.sin(Math.PI * 135 / 180))) * value + startY);

                iv_2.setX((float) ((radius * Math.cos(Math.PI * 180 / 180))) * value + startX);
                iv_2.setY((float) ((radius * Math.sin(Math.PI * 180 / 180))) * value + startY);

                iv_3.setX((float) ((radius * Math.cos(Math.PI * 225 / 180))) * value + startX);
                iv_3.setY((float) ((radius * Math.sin(Math.PI * 225 / 180))) * value + startY);

                iv_4.setX((float) ((radius * Math.cos(Math.PI * 270 / 180))) * value + startX);
                iv_4.setY((float) ((radius * Math.sin(Math.PI * 270 / 180))) * value + startY);

                iv_5.setX((float) ((radius * Math.cos(Math.PI * 315 / 180))) * value + startX);
                iv_5.setY((float) ((radius * Math.sin(Math.PI * 315 / 180))) * value + startY);
            }
        });
    }

    private void boom(final ImageView imageView) {
        Bloom.with((Activity) getContext())
                .setParticleRadius(5)
                .setBloomListener(new BloomListener() {
                    @Override
                    public void onBegin() {
                        //动画开始
                    }

                    @Override
                    public void onEnd() {
                        //动画结束
                        imageView.setVisibility(View.GONE);
                    }
                })
                .boom(imageView);
    }

    private int getBarHeight() {
        Rect rect = new Rect();
//getWindow().getDecorView()得到的View是Window中的最顶层View，可以从Window中获取到该View，
//然后该View有个getWindowVisibleDisplayFrame()方法可以获取到程序显示的区域，
//包括标题栏，但不包括状态栏。
        ((Activity) getContext()).getWindow().getDecorView().getWindowVisibleDisplayFrame(rect);
//1.获取状态栏高度：
//根据上面所述，我们可以通过rect对象得到手机状态栏的高度
        int statusBarHeight = rect.top;
//2.获取标题栏高度：
        ((Activity) getContext()).getWindow().findViewById(Window.ID_ANDROID_CONTENT);
//该方法获取到的View是程序不包括标题栏的部分，这样我们就可以计算出标题栏的高度了。
        int contentTop = ((Activity) getContext()).getWindow().findViewById(Window.ID_ANDROID_CONTENT).getTop();
//statusBarHeight是上面所求的状态栏的高度
        return contentTop - statusBarHeight;
    }

}
