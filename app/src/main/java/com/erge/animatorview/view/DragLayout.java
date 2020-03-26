package com.erge.animatorview.view;

import android.animation.ValueAnimator;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.erge.animatorview.R;

/**
 * Created by erge 2019-12-30 20:34
 */
public class DragLayout extends FrameLayout implements View.OnClickListener {

    private View ll_left;
    private View ll_top;
    private View ll_middle;
    private View ll_bottom;
    private View leftView;

    public DragLayout(@NonNull Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    private void init() {
        View view = LayoutInflater.from(getContext()).inflate(R.layout.layout_drag, this);
        ll_left = view.findViewById(R.id.ll_left);
        ll_top = view.findViewById(R.id.ll_top);
        ll_middle = view.findViewById(R.id.ll_middle);
        ll_bottom = view.findViewById(R.id.ll_bottom);
        leftView = ll_left;

//        ll_left.setOnClickListener(this);
        ll_top.setOnClickListener(this);
        ll_middle.setOnClickListener(this);
        ll_bottom.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        trans(v);
    }

    private void trans(final View target) {
        final int leftHeight = leftView.getHeight();
        final int rightHeight = target.getHeight();

        final ViewGroup.LayoutParams leftParams = leftView.getLayoutParams();
        final ViewGroup.LayoutParams rightParams = target.getLayoutParams();

        ValueAnimator animator = ValueAnimator.ofFloat(0f, 1f);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                float x = value * (target.getLeft() - leftView.getLeft());
                float y = value * (target.getTop() - leftView.getTop());

                leftParams.height = (int) (leftHeight - rightHeight * 2 * value);
                rightParams.height = (int) (rightHeight + rightHeight * 2 * value);

                leftView.setLayoutParams(leftParams);
                target.setLayoutParams(rightParams);

                leftView.setTranslationX(x);
                leftView.setTranslationY(y);

                target.setTranslationX(-x);
                target.setTranslationY(-y);

                if (value == 1f) {
                    leftView = target;
                }
            }
        });
        animator.setDuration(2000);
        animator.start();
    }

}
