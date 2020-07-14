package com.erge.animatorview.activity;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.erge.animatorview.R;
import com.erge.animatorview.view.OvalView;
import com.erge.animatorview.view.VerticalScrollView;

/**
 * Created by erge 2020-03-26 16:10
 */
public class ScrollActivity2 extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_scroll);

        VerticalScrollView scrollView = findViewById(R.id.scrollView);
        final View viewTrans = findViewById(R.id.viewTrans);
        final OvalView ovalView = findViewById(R.id.viewOval);
        scrollView.setOnScrollListener(new VerticalScrollView.OnScrollListener() {
            @Override
            public void onScroll(int instance, int maxInstance, float alpha, boolean up) {
//                ovalView.switchColor(alpha == 1f);
                viewTrans.setAlpha(alpha);
            }
        });
    }
}
