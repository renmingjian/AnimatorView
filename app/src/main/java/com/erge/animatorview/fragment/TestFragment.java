package com.erge.animatorview.fragment;

import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.erge.animatorview.R;
import com.youth.banner.Banner;
//import com.youth.banner.loader.ImageLoader;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by erge 2020-03-18 10:43
 */
public class TestFragment extends Fragment implements View.OnClickListener {

    private Banner recommend_banner;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return LayoutInflater.from(container.getContext()).inflate(R.layout.fragment_test, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        recommend_banner = view.findViewById(R.id.recommend_banner);
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        List<String> list = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            list.add("https://www.keaidian.com/uploads/allimg/190430/30113448_0.jpg");
        }

//        recommend_banner.set(list);
//        recommend_banner.isAutoPlay(true);
//        recommend_banner.setDelayTime(2000);
//        recommend_banner.setImageLoader(new ImageLoader() {
//            @Override
//            public void displayImage(Context context, Object path, ImageView imageView) {
//                Glide.with(context).load(path).into(imageView);
//            }
//        });
        recommend_banner.start();
    }

    @Override
    public void onClick(View v) {

    }

}
