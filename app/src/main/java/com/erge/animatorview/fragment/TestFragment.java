package com.erge.animatorview.fragment;

import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.graphics.Rect;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.erge.animatorview.R;
import com.erge.animatorview.utils.Utils;
import com.erge.animatorview.view.ChargeProgressView;
import com.erge.animatorview.view.MultiImagesView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erge 2020-03-18 10:43
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private MultiImagesView imageView;
    private ImageView iv;
    private ChargeProgressView chargeProgressView;
    private ImageView iv_car;
    private int screenWidth;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        imageView = view.findViewById(R.id.imageView);
        iv = view.findViewById(R.id.iv_test);

        chargeProgressView = view.findViewById(R.id.chargeView);
//        chargeProgressView.setProgress(1);

        view.findViewById(R.id.button_charge).setOnClickListener(this);

//        view.findViewById(R.id.chargeBgView).setRotationX(50);
        iv_car = view.findViewById(R.id.iv_car);

        Rect screen = Utils.getScreen(getContext());
        screenWidth = screen.right;
        iv_car.setScaleX(0.5f);
        iv_car.setScaleY(0.5f);
        iv_car.setTranslationX(screenWidth >> 1);
//        iv_car.setTranslationY(-Utils.dp2px(80));
        carAnim();
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 1; i++) {
            list.add("https://www.keaidian.com/uploads/allimg/190430/30113448_0.jpg");
        }
        imageView.setImages(list, true, new MultiImagesView.OnImageClickListener() {
            @Override
            public void onImageClick(List<String> images, int position) {
                Toast.makeText(getContext(), "" + position, Toast.LENGTH_SHORT).show();
            }
        });

        Glide.with(getContext()).load("https://www.keaidian.com/uploads/allimg/190430/30113448_0.jpg").into(iv);
    }

    @Override
    public void onClick(View v) {
        ObjectAnimator animator = ObjectAnimator.ofInt(chargeProgressView, "progress", 1, 100);
        animator.setDuration(4000);
        animator.start();
    }

    private void carAnim() {
        ValueAnimator animator = ValueAnimator.ofFloat(0.5f, 1f);
        animator.setDuration(2000);
        animator.addUpdateListener(new ValueAnimator.AnimatorUpdateListener() {
            @Override
            public void onAnimationUpdate(ValueAnimator animation) {
                float value = (float) animation.getAnimatedValue();
                iv_car.setScaleX(value);
                iv_car.setScaleY(value);

                iv_car.setTranslationX(screenWidth * (1 - value));
//                iv_car.setTranslationY((2 * value - 1) * Utils.dp2px(80));

//                setMargin((int) ((1f - value) * screenWidth));
                if (value == 1) {
//                    iv_car.setTranslationY((2 * value - 1) * Utils.dp2px(80));
                }
            }
        });
        animator.start();
    }

}
