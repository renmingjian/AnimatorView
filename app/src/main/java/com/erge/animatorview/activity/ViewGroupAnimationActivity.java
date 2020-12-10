package com.erge.animatorview.activity;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.erge.animatorview.R;
import com.erge.animatorview.view.FloatingView;

public class ViewGroupAnimationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_group_animation);

        ViewGroup root = findViewById(R.id.root);
        @SuppressLint("InflateParams")
        View view = LayoutInflater.from(this).inflate(R.layout.floatint_layout, null);

        FloatingView floatingView = new FloatingView.Builder()
                .attachRootLayout(root)
                .setContentView(view)
                .setMarginX(20)
                .setMarginY(20)
                .setCanDrag(true)
                .setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Toast.makeText(ViewGroupAnimationActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
                    }
                })
                .build(this);

        floatingView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(ViewGroupAnimationActivity.this, "点击事件", Toast.LENGTH_SHORT).show();
            }
        });
    }
}